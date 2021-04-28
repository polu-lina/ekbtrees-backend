package ru.naumen.ectmapi.converter;

import org.mapstruct.Mapper;
import ru.naumen.ectmapi.entity.Comment;
import ru.naumen.ectmapi.dto.СommentDto;

@Mapper(componentModel = "spring")
public interface CommentConverter {

    СommentDto toDto(Comment comment);

    Comment fromDto(СommentDto commentDto);
}
