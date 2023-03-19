package com.br.common.exception;

import org.springframework.http.HttpStatus;

/**
 * author: anct
 * date: 30/10/2022
 * YNWA
 */
public enum CommonErrors implements ErrorCode {

    JSON_ERROR("C-001", HttpStatus.INTERNAL_SERVER_ERROR, "Invalid json."),
    NULL_POINTER("C-002", HttpStatus.INTERNAL_SERVER_ERROR),
    IO_EXCEPTION("C-003", HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong."),
    INVALID_ARG("C-004", HttpStatus.BAD_REQUEST, "Invalid argument value."),
    MISSING_FIELD("C-005", HttpStatus.BAD_REQUEST, "Field %s missing."),
    MISSING_HEADER("C-006", HttpStatus.BAD_REQUEST, "Header %s missing.");

    private final String code;
    private final HttpStatus status;
    private String message;

    CommonErrors(String code,
                 HttpStatus status,
                 String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }

    CommonErrors(String code, HttpStatus status) {
        this.code = code;
        this.status = status;
    }

    @Override
    public HttpStatus status() {
        return status;
    }

    @Override
    public String code() {
        return code;
    }

    @Override
    public String message() {
        return message;
    }
}
