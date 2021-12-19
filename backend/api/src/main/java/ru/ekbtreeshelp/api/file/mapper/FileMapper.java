package ru.ekbtreeshelp.api.file.mapper;


import org.mapstruct.Mapper;
import ru.ekbtreeshelp.api.file.dto.FileDto;
import ru.ekbtreeshelp.core.entity.FileEntity;

@Mapper(componentModel = "spring")
public interface FileMapper {

    FileDto toDto(FileEntity fileEntity);

    FileEntity fromDto(FileDto fileDto);

}
