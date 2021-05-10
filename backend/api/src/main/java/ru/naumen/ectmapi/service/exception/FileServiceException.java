package ru.naumen.ectmapi.service.exception;

public class FileServiceException extends RuntimeException {
    public FileServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
