package ru.naumen.ectmapi.controllers;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.postgis.Point;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.naumen.ectmapi.converter.TreeMapInfoConverter;
import ru.naumen.ectmapi.dto.TreeMapInfoDto;
import ru.naumen.ectmapi.service.TreeMapInfoService;

import java.util.List;

@RestController
@RequestMapping(value = "api/tree-map-info", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class TreeMapInfoController {

    private final TreeMapInfoService treeMapInfoService;
    private final TreeMapInfoConverter treeMapInfoConverter;

    @Operation(summary = "Предоставляет информацию о деревьях, входящих в указанную область")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/get-in-region")
    public List<TreeMapInfoDto> getInRegion(
            @RequestParam("x1") double topLeftLatitude,
            @RequestParam("y1") double topLeftLongitude,
            @RequestParam("x2") double bottomRightLatitude,
            @RequestParam("y2") double bottomRightLongitude
    ) {
        Point topLeftPoint = new Point(topLeftLatitude, topLeftLongitude);
        Point bottomRightPoint = new Point(bottomRightLatitude, bottomRightLongitude);
        return treeMapInfoConverter.toDto(treeMapInfoService.getInRegion(topLeftPoint, bottomRightPoint));
    }

    @Operation(summary = "Предоставляет информацию о деревьях текущего пользователя")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/get")
    public List<TreeMapInfoDto> get() {
        /**
         * Тут мы будем получать пользователя из контекста.
         * authorId - временная заглушка.
         * */
        Long authorId = 1L; //FIXME

        return treeMapInfoConverter.toDto(treeMapInfoService.getAllByAuthorId(authorId));
    }
}

