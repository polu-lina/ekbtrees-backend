package ru.naumen.ectmapi.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class BaseErrorDto extends AbstractBaseDto {
    private final ResponseType responseType = ResponseType.error;
    private final String data;
}

