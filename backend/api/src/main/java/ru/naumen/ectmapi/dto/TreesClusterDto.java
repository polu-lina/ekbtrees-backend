package ru.naumen.ectmapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Schema(description = "Кластер деревьев")
@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class TreesClusterDto {

    @Schema(description = "Географическая точка, центр кластера")
    private GeographicalPointDto centre;

    @Schema(description = "Количество деревьев в кластере")
    private int count;
}
