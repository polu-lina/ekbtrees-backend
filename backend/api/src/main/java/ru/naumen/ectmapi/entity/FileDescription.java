package ru.naumen.ectmapi.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class FileDescription extends Entity<Long> {

    /**
     * Название файла
     */
    private String title;

    /**
     * Тип файла
     */
    private String mimeType;

    /**
     * Размер файла
     */
    private Long size;

    /**
     * Ссылка, переход по которой инициирует загрузку контента файла
     */
    private String uri;

    private String hash;

    private Long treeId;
}
