package ru.naumen.ectmapi.converter;

import org.mapstruct.Mapper;
import ru.naumen.ectmapi.entity.Comment;
import ru.naumen.ectmapi.dto.CommentDto;

@Mapper(componentModel = "spring")
public interface CommentConverter {

    CommentDto toDto(Comment comment);

    Comment fromDto(CommentDto commentDto);
}
