package ru.ekbtreeshelp.api.tree.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.ekbtreeshelp.api.tree.mapper.SpeciesTreeMapper;
import ru.ekbtreeshelp.api.tree.dto.SpeciesTreeDto;
import ru.ekbtreeshelp.api.tree.service.SpeciesTreeService;

import java.util.List;

@Tag(name = "Операции с породами деревьев")
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "api/species", produces = MediaType.APPLICATION_JSON_VALUE)
public class SpeciesTreeController {

    private final SpeciesTreeService speciesTreeService;
    private final SpeciesTreeMapper speciesTreeMapper;

    @SecurityRequirement(name = "jwt")
    @Operation(summary = "Сохраняет новую породу")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority(@Roles.SUPERUSER, @Roles.MODERATOR)")
    public Long save (@RequestBody SpeciesTreeDto speciesTreeDto) {
        return speciesTreeService.save(speciesTreeMapper.fromDto(speciesTreeDto));
    }

    @Operation(summary = "Предоставляет список пород деревьев")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/get-all")
    @PreAuthorize("permitAll()")
    public List<SpeciesTreeDto> getAll() {
        return speciesTreeMapper.toDto(speciesTreeService.getAll());
    }

    @SecurityRequirement(name = "jwt")
    @Operation(summary = "Удаляет породу")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority(@Roles.SUPERUSER, @Roles.MODERATOR)")
    public void delete(@PathVariable Long id) {
        speciesTreeService.delete(id);
    }
}
