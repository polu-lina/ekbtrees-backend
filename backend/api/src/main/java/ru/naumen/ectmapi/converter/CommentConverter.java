package ru.naumen.ectmapi.converter;

import org.mapstruct.Mapper;
import ru.naumen.ectmapi.entity.Comment;
import ru.naumen.ectmapi.dto.CommentDto;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CommentConverter {

    CommentDto toDto(Comment comment);

    List<CommentDto> toDto(List<Comment> comments);

    Comment fromDto(CommentDto commentDto);
}
