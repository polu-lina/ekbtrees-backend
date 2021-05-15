package ru.naumen.ectmapi.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;

@EqualsAndHashCode(callSuper = true)
@Data
public class Comment extends Entity<Long> {

    /**
     * Дата и время добавления комментария
     */
    private Instant created;

    /**
     * Ссылка на автора
     */
    private Long authorId;

    /**
     * Текст комментария
     */
    private String text;

    /**
     * Идентификатор дерева, к которому относиться комментарий
     */
    private Long treeId;
}
