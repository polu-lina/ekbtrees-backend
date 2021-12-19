package ru.ekbtreeshelp.api.util.mapper;

import org.mapstruct.Mapper;

import java.util.Date;

@Mapper(componentModel = "spring")
public interface DatesMapper {
    default Long map(Date value) {
        return value.getTime();
    }
}
