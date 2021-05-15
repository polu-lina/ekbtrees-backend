package ru.naumen.ectmapi.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.naumen.ectmapi.converter.FileConverter;
import ru.naumen.ectmapi.dto.FileDto;
import ru.naumen.ectmapi.service.FileService;

@RestController
@RequestMapping("api/file")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
public class FileController {

    private final FileService fileService;
    private final FileConverter fileConverter;

    @Operation(
            summary = "Загружает файл в хранилище",
            responses = {@ApiResponse(description = "id загружаемого файла")}
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

    @Operation(summary = "Удаляет файл по id")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        fileService.delete(id);
    }
}
