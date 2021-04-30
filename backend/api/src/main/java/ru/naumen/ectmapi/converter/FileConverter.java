package ru.naumen.ectmapi.converter;


import org.mapstruct.Mapper;
import ru.naumen.ectmapi.dto.FileDto;
import ru.naumen.ectmapi.entity.FileDescription;

@Mapper(componentModel = "spring")
public interface FileConverter {

    FileDto toDto(FileDescription fileDescription);

    FileDescription fromDto(FileDto fileDto);

}
