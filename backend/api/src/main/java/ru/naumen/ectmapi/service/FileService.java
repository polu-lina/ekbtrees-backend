package ru.naumen.ectmapi.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;
import ru.naumen.ectmapi.entity.FileDescription;
import ru.naumen.ectmapi.repository.FileRepository;
import ru.naumen.ectmapi.service.exception.FileServiceException;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class FileService {

    private static final String BUCKET = "ectm";

    private final FileRepository fileRepository;
    private final AmazonS3 s3;

    public FileDescription save(MultipartFile file) {
        return fileRepository.save(saveToS3(file));
    }

    public FileDescription get(Long id) {
        return fileRepository.getOne(id);
    }

    public void delete(Long id) {
        FileDescription fileToDelete = fileRepository.getOne(id);

        if (fileRepository.countByHash(fileToDelete.getHash()) == 1) {
            deleteFromS3(fileToDelete);
        }

        fileRepository.deleteById(id);
    }

    private FileDescription saveToS3(MultipartFile file) {

        String uri;
        String hash;
        long fileSize;

        File tempFile = null;

        try(InputStream inputStream = file.getInputStream()) {

            tempFile = File.createTempFile(UUID.randomUUID().toString(), "tmp");
            FileUtils.copyInputStreamToFile(inputStream, tempFile);

            fileSize = tempFile.length();
            hash = DatatypeConverter.printHexBinary(
                    DigestUtils.md5Digest(FileUtils.readFileToByteArray(tempFile))
            ).toUpperCase();

            FileDescription sameHashFile = fileRepository.getFirstByHash(hash);

            if (sameHashFile == null) {

                s3.putObject(
                        new PutObjectRequest(BUCKET, hash, tempFile)
                                .withCannedAcl(CannedAccessControlList.PublicRead)
                );

                uri = s3.getUrl(BUCKET, hash).toString();

            } else {
                uri = sameHashFile.getUri();
            }

        } catch (Exception e) {
            throw new FileServiceException(e.getMessage(), e);
        } finally {
            if (tempFile != null) {
                try {
                    FileUtils.forceDelete(tempFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        FileDescription fileDescription = new FileDescription();
        fileDescription.setTitle(file.getOriginalFilename());
        fileDescription.setMimeType(file.getContentType());
        fileDescription.setSize(fileSize);
        fileDescription.setUri(uri);
        fileDescription.setHash(hash);

        return fileDescription;
    }

    private void deleteFromS3(FileDescription fileToDelete) {
        s3.deleteObject(BUCKET, fileToDelete.getHash());
    }
}
