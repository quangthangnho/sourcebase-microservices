package com.br.common.exception;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
    HttpStatus status();

    String code();

    String message();
}