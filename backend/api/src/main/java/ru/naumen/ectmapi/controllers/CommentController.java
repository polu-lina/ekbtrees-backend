package ru.naumen.ectmapi.controllers;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.naumen.ectmapi.converter.CommentConverter;
import ru.naumen.ectmapi.dto.CommentDto;
import ru.naumen.ectmapi.service.CommentService;


@RestController
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@RequestMapping(value = "api/comment", produces = MediaType.APPLICATION_JSON_VALUE)
public class CommentController {

    private final CommentService commentService;
    private final CommentConverter commentConverter;

    @Operation(summary = "Сохраняет комментарий")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/save")
    public void save(@RequestBody CommentDto commentDto){
        commentService.save(commentConverter.fromDto(commentDto));
    }
  
    @Operation(summary = "Предоставляет комментарий по id")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/get/{id}")
    public CommentDto get(@PathVariable Long id) {
        return commentConverter.toDto(commentService.get(id));
    }

    @Operation(summary = "Удаляет комментарий по id")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id){ commentService.delete(id);}
}
