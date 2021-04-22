package ru.naumen.ectmapi.controllers;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.naumen.ectmapi.dto.TypeOfTreeDto;

@RestController
@RequestMapping(value = "api/type", produces = MediaType.APPLICATION_JSON_VALUE)
public class TypeOfTreeController {

    @Operation(summary = "Создаёт новую породу")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/save")
    public void save(@RequestBody TypeOfTreeDto typeOfTreeDto){

    }

    @Operation(summary = "Изменяет информацию о породе")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/update")
    public void update(@RequestBody @Valid TypeOfTreeDto typeOfTreeDto){

    }

    @Operation(summary = "Удаляет породу")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id){

    }
}