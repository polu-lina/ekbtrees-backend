package ru.ekbtreeshelp.api.comment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Schema(description = "Транспортная сущность редактируемого комментария")
public class UpdateCommentDto {
    @NotNull
    @Schema(description = "Текст комментария")
    private String text;
}
