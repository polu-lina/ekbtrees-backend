package ru.naumen.ectmapi.controllers;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.naumen.ectmapi.converter.SpeciesTreeConverter;
import ru.naumen.ectmapi.dto.SpeciesTreeDto;
import ru.naumen.ectmapi.service.SpeciesTreeService;

import java.util.List;

@RestController
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@RequestMapping(value = "api/species", produces = MediaType.APPLICATION_JSON_VALUE)
public class SpeciesTreeController {

    private final SpeciesTreeService speciesTreeService;
    private final SpeciesTreeConverter speciesTreeConverter;

    @Operation(summary = "Сохраняет новую породу")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/save")
    @PreAuthorize("hasAnyRole(@Roles.SUPERUSER, @Roles.MODERATOR)")
    public void save(@RequestBody SpeciesTreeDto speciesTreeDto){
        speciesTreeService.save(speciesTreeConverter.fromDto(speciesTreeDto));
    }

    @Operation(summary = "Предоставляет список пород деревьев")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/get-all")
    public List<SpeciesTreeDto> getAll(){
        return speciesTreeConverter.toDto(speciesTreeService.getAll());
    }

    @Operation(summary = "Удаляет породу")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyRole(@Roles.SUPERUSER, @Roles.MODERATOR)")
    public void delete(@PathVariable Long id){
        speciesTreeService.delete(id);
    }
}