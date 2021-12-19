package ru.ekbtreeshelp.api.file.service.exception;

public class FileServiceException extends RuntimeException {
    public FileServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
