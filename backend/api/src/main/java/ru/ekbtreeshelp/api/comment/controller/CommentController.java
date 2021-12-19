package ru.ekbtreeshelp.api.comment.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.ekbtreeshelp.api.comment.mapper.CommentMapper;
import ru.ekbtreeshelp.api.comment.dto.CommentDto;
import ru.ekbtreeshelp.api.comment.dto.CreateCommentDto;
import ru.ekbtreeshelp.api.comment.dto.UpdateCommentDto;
import ru.ekbtreeshelp.api.comment.service.CommentService;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;

@Tag(name = "Операции с комментариями")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/comment", produces = MediaType.APPLICATION_JSON_VALUE)
public class CommentController {

    private final CommentService commentService;
    private final CommentMapper commentMapper;

    @SecurityRequirement(name = "jwt")
    @Operation(summary = "Создает новый комментарий")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public Long create(@RequestBody @Valid CreateCommentDto createCommentDto) {
        return commentService.create(createCommentDto);
    }

    @Operation(summary = "Предоставляет комментарии по id дерева")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/by-tree/{treeId}")
    @PreAuthorize("permitAll()")
    public List<CommentDto> getAllByTreeId(@PathVariable Long treeId) {
        return commentMapper.toDto(commentService.getAllByTreeId(treeId));
    }

    @SecurityRequirement(name = "jwt")
    @Operation(summary = "Редактирует существующий комментарий")
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority(@Roles.MODERATOR, @Roles.SUPERUSER) " +
            "or hasPermission(#id, @Domains.COMMENT, @Permissions.EDIT)")
    public void update(@PathVariable Long id,
                       @RequestBody @Valid UpdateCommentDto updateCommentDto) {
        try {
            commentService.update(id, updateCommentDto);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @SecurityRequirement(name = "jwt")
    @Operation(summary = "Удаляет комментарий по id")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyAuthority(@Roles.MODERATOR, @Roles.SUPERUSER) " +
            "or hasPermission(#id, @Domains.COMMENT, @Permissions.DELETE)")
    public void delete(@PathVariable Long id){
        commentService.delete(id);
    }
}
