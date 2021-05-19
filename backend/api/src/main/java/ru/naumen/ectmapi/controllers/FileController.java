package ru.naumen.ectmapi.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.naumen.ectmapi.converter.FileConverter;
import ru.naumen.ectmapi.dto.FileDto;
import ru.naumen.ectmapi.service.FileService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/file")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class FileController {

    private final FileService fileService;
    private final FileConverter fileConverter;

    @Operation(
            summary = "Загружает файл в хранилище",
            responses = {
                    @ApiResponse(
                            description = "id загружаемого файла"
                    )
            },
            requestBody = @RequestBody(
                    description = "Формат multipart/form-data, один файл с ключом 'file'",
                    required = true,
                    content = {
                            @Content(
                                    mediaType = "multipart/form-data",
                                    schema = @Schema(
                                            type = "object",
                                            requiredProperties = "file"
                                    )
                            )
                    }
            )
    )
    @PostMapping("/upload")
    public Long upload(@RequestParam("file") MultipartFile file) {
        return fileService.save(file).getId();
    }

    @Operation(summary = "Предоставляет файл по id")
    @GetMapping("/{id}")
    public FileDto get(@PathVariable Long id) {
        return fileConverter.toDto(fileService.get(id));
    }

    @Operation(summary = "Предоставляет файлы по id дерева, с которым они связаны")
    @GetMapping("/byTree/{treeId}")
    public List<FileDto> listByTreeId(@PathVariable Long treeId) {
        return fileService.listByTreeId(treeId)
                .stream()
                .map(fileConverter::toDto)
                .collect(Collectors.toList());
    }

    @Operation(summary = "Удаляет файл по id")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        fileService.delete(id);
    }
}
