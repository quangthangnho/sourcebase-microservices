package com.br.common.exception;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Getter;

@Getter
public class ErrorCodeException extends RuntimeException {
    private final String message;
    private final transient ErrorCode errorCode;
    private final transient JsonNode description;

    public ErrorCodeException(ErrorCode errorCode) {
        this.errorCode = errorCode;
        this.message = errorCode.message();
        this.description = null;
    }

    public ErrorCodeException(ErrorCode errorCode, String message) {
        this.message = message;
        this.errorCode = errorCode;
        this.description = null;
    }

    public ErrorCodeException(ErrorCode errorCode,
                              String message,
                              JsonNode description) {
        this.message = message;
        this.errorCode = errorCode;
        this.description = description;
    }

    public ErrorCodeException(Throwable cause,
                              ErrorCode errorCode,
                              String message,
                              JsonNode description) {
        super(cause);
        this.message = message;
        this.errorCode = errorCode;
        this.description = description;
    }
}