package ru.naumen.ectmapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Schema(description = "Результат проверки здоровья приложения")
@AllArgsConstructor
public class HealthCheckDto {

    @Schema(description = "Информация о статусе приложения")
    private String statusInfo;

}
