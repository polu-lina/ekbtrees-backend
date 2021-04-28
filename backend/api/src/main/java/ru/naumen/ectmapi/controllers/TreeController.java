package ru.naumen.ectmapi.controllers;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.naumen.ectmapi.converter.TreeConverter;
import ru.naumen.ectmapi.dto.TreeDto;
import ru.naumen.ectmapi.entity.Tree;
import ru.naumen.ectmapi.service.TreeService;

import javax.validation.Valid;


@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/tree", produces = MediaType.APPLICATION_JSON_VALUE)
public class TreeController {

    private final TreeService treeService;
    private final TreeConverter treeConverter;

    @Operation(summary = "Сохраняет дерево")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/save")
    public void save(TreeDto treeDto){
        treeService.save(treeConverter.fromDto(treeDto));
    }

    @Operation(summary = "Предоставляет дерево по id")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/get/{id}")
    public TreeDto get(@PathVariable Long id) {
        return treeConverter.toDto(treeService.get(id));
    }

    @Operation(summary = "Удаляет дерево по id")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id){ treeService.delete(id);}

}

