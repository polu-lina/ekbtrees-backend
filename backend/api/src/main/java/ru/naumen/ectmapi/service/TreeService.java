package ru.naumen.ectmapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.naumen.ectmapi.converter.TreeConverter;
import ru.naumen.ectmapi.dto.TreeDto;
import ru.naumen.ectmapi.entity.Tree;
import ru.naumen.ectmapi.repository.FileDescriptionRepository;
import ru.naumen.ectmapi.repository.TreeRepository;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class TreeService {

    private final TreeRepository treeRepository;
    private final FileDescriptionRepository fileDescriptionRepository;
    private final TreeConverter treeConverter;

    public void save(TreeDto tree) {
        Tree treeEntity = treeConverter.fromDto(tree);

        if (treeEntity.isNew()) {
            treeRepository.create(treeEntity);
        } else {
            treeRepository.update(treeEntity);
        }

        tree.getFileIds().forEach(fileId -> fileDescriptionRepository.updateTreeId(fileId, treeEntity.getId()));
    }

    public Tree get(Long id){
        return treeRepository.find(id);
    }

    public void delete(Long id) {
        treeRepository.delete(id);
    }
}
