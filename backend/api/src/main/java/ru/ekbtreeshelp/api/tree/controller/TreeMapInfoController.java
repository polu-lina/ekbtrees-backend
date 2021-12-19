package ru.ekbtreeshelp.api.tree.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.postgis.Point;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.ekbtreeshelp.api.tree.mapper.TreeMapInfoMapper;
import ru.ekbtreeshelp.api.tree.dto.TreeMapInfoDto;
import ru.ekbtreeshelp.api.security.service.SecurityService;
import ru.ekbtreeshelp.api.tree.service.TreeMapInfoService;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@Tag(name = "Операции с краткой информацией о деревьях")
@Validated
@RestController
@RequestMapping(value = "api/tree-map-info", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class TreeMapInfoController {

    private final TreeMapInfoService treeMapInfoService;
    private final TreeMapInfoMapper treeMapInfoMapper;
    private final SecurityService securityService;

    @Operation(summary = "Предоставляет сокращенную информацию о деревьях, входящих в указанную область")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/get-in-region")
    @PreAuthorize("permitAll()")
    public List<TreeMapInfoDto> getInRegion(
            @RequestParam("x1") double topLeftLatitude,
            @RequestParam("y1") double topLeftLongitude,
            @RequestParam("x2") double bottomRightLatitude,
            @RequestParam("y2") double bottomRightLongitude
    ) {
        Point topLeftPoint = new Point(topLeftLatitude, topLeftLongitude);
        Point bottomRightPoint = new Point(bottomRightLatitude, bottomRightLongitude);
        return treeMapInfoMapper.toDto(treeMapInfoService.getInRegion(topLeftPoint, bottomRightPoint));
    }

    @SecurityRequirement(name = "jwt")
    @Deprecated
    @Operation(summary = "Предоставляет сокращенную информацию о деревьях текущего пользователя")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/get")
    @PreAuthorize("isAuthenticated()")
    public List<TreeMapInfoDto> get() {
        Long authorId = securityService.getCurrentUserId();
        return treeMapInfoMapper.toDto(treeMapInfoService.getAllByAuthorId(authorId, 0, 100));
    }

    @SecurityRequirement(name = "jwt")
    @Operation(summary = "Предоставляет сокращенную информацию о деревьях текущего пользователя")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/get/{page}/{size}")
    @PreAuthorize("isAuthenticated()")
    public List<TreeMapInfoDto> getCurrentUserTreeInfos(@PathVariable @Min(0) Integer page,
                                                        @PathVariable @Min(1) @Max(100) Integer size) {
        Long authorId = securityService.getCurrentUserId();
        return treeMapInfoMapper.toDto(treeMapInfoService.getAllByAuthorId(authorId, page, size));
    }
}
