package ru.ekbtreeshelp.api.comment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Schema(description = "Транспортная сущность нового комментария")
public class CreateCommentDto {
    @NotNull
    @Schema(description = "Текст комментария")
    private String text;

    @NotNull
    @Schema(description = "Идентификатор дерева, к которому относится комментарий")
    private Long treeId;
}
