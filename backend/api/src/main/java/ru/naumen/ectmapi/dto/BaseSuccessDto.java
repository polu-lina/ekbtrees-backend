package ru.naumen.ectmapi.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class BaseSuccessDto<T> extends AbstractBaseDto {
    private final ResponseType responseType = ResponseType.success;
    private final T data;
}