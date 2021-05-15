package ru.naumen.ectmapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "Кластер деревьев")
@Data
public class TreesClusterDto {

    @Schema(description = "Географическая точка, центр кластера")
    private GeographicalPointDto centre;

    @Schema(description = "Количество деревьев в кластере")
    private int count;
}
