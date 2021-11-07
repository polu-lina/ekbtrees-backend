package ru.ekbtreeshelp.admin.example.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class ExampleResponseDto {
    Long id;
    Date creationDate;
    String someValue;
}
