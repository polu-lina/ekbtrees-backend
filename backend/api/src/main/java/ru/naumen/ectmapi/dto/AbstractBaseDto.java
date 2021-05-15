package ru.naumen.ectmapi.dto;

import lombok.Data;

import static ru.naumen.ectmapi.Constants.API_VERSION;

@Data
public abstract class AbstractBaseDto {
    protected enum ResponseType {
        error,
        success
    }
    protected final int apiVersion = API_VERSION;
}
