package ru.naumen.ectmapi.controllers;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.postgis.Point;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.naumen.ectmapi.converter.TreesClusterConverter;
import ru.naumen.ectmapi.dto.TreesClusterDto;
import ru.naumen.ectmapi.service.TreesClusterService;

import java.util.List;

@RestController
@RequestMapping(value = "api/trees-cluster", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class TreesClusterController {

    private final TreesClusterService treesClusterService;
    private final TreesClusterConverter treesClusterConverter;

    @Operation(summary = "Предоставляет информацию о кластерах деревьев, входящих в указаную область")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/get-in-region")
    public List<TreesClusterDto> getInRegion(
            @RequestParam("x1") double topLeftLatitude,
            @RequestParam("y1") double topLeftLongitude,
            @RequestParam("x2") double bottomRightLatitude,
            @RequestParam("y2") double bottomRightLongitude
    ) {
        Point topLeftPoint = new Point(topLeftLatitude, topLeftLongitude);
        Point bottomRightPoint = new Point(bottomRightLatitude, bottomRightLongitude);
        return treesClusterConverter.toDto(treesClusterService.getInRegion(topLeftPoint, bottomRightPoint));
    }
}
