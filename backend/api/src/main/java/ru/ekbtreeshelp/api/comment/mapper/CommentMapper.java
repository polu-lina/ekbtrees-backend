package ru.ekbtreeshelp.api.comment.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.ekbtreeshelp.api.comment.dto.CommentDto;
import ru.ekbtreeshelp.api.comment.dto.CreateCommentDto;
import ru.ekbtreeshelp.api.comment.dto.UpdateCommentDto;
import ru.ekbtreeshelp.api.tree.mapper.TreeMapper;
import ru.ekbtreeshelp.core.entity.Comment;

import java.util.List;

@Mapper(componentModel = "spring",
        uses = {
               TreeMapper.class
        }
)
public interface CommentMapper {

    CommentDto toDto(Comment comment);

    List<CommentDto> toDto(List<Comment> comments);

    Comment fromDto(CommentDto commentDto);

    @Mapping(source = "treeId", target = "tree")
    Comment fromDto(CreateCommentDto createCommentDto);

    void updateFromDto(UpdateCommentDto updateCommentDto, @MappingTarget Comment comment);
}
