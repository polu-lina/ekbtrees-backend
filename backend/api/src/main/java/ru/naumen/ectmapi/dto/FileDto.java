package ru.naumen.ectmapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@Schema
@AllArgsConstructor
public class FileDto {

    @Schema(description = "Идентификатор файла")
    private Long id;

    @Schema(description = "Название файла")
    private String title;

    @Schema(description = "Тип файла")
    private String mimeType;

    @Schema(description = "Размер файла")
    private Long size;

    @Schema(description = "Ссылка, переход по которой инициирует загрузку контента файла")
    private String uri;

}
