package com.br.authentication.exception;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.br.common.controller.BaseController;
import com.br.common.exception.CommonErrors;
import com.br.common.exception.ErrorCode;
import com.br.common.exception.ErrorCodeException;
import com.br.common.exception.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.core.codec.DecodingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;
import java.util.Objects;

/**
 * @author hungdt
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends BaseController {

    @ExceptionHandler(ErrorCodeException.class)
    public ResponseEntity<ErrorResponse> handleErrorCodeException(ErrorCodeException ex) {
        return toErrorResponse(ex);
    }

    @ExceptionHandler(DecodingException.class)
    public ResponseEntity<ErrorResponse> handleDecodingException(DecodingException ex) {
        return toErrorResponse(CommonErrors.JSON_ERROR, ex);
    }

    @ExceptionHandler(JsonMappingException.class)
    public ResponseEntity<ErrorResponse> handleJsonMappingException(JsonMappingException ex) {
        return toErrorResponse(CommonErrors.JSON_ERROR, ex);
    }

    @ExceptionHandler(JsonProcessingException.class)
    public ResponseEntity<ErrorResponse> handleJsonProcessingException(JsonProcessingException ex) {
        return toErrorResponse(CommonErrors.JSON_ERROR, ex);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorResponse> handleNullPointerException(NullPointerException ex) {
        return toErrorResponse(CommonErrors.NULL_POINTER, ex);
    }

    @ExceptionHandler(IOException.class)
    public ResponseEntity<ErrorResponse> handleIOException(IOException ex) {
        return toErrorResponse(CommonErrors.IO_EXCEPTION, ex);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgNotValid(MethodArgumentNotValidException ex) {
        var fieldError = ex.getBindingResult().getFieldError();
        var errorCode = CommonErrors.INVALID_ARG;
        var message = errorCode.message();
        if (fieldError != null && Objects.isNull(fieldError.getRejectedValue())) {
            errorCode = CommonErrors.MISSING_FIELD;
            message = String.format(errorCode.message(), fieldError.getField());
        }

        var errorResp = new ErrorResponse(RandomStringUtils.randomAlphabetic(5), errorCode.code(), message);
        return toResponseEntity(errorResp, errorCode.status());
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ErrorResponse> handleMissingHeaderException(MissingRequestHeaderException ex) {
        var errorCode = CommonErrors.MISSING_HEADER;
        var message = String.format(errorCode.message(), ex.getHeaderName());
        var errorResp = new ErrorResponse(RandomStringUtils.randomAlphabetic(5), errorCode.code(), message);
        return toResponseEntity(errorResp, errorCode.status());
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)  // Nếu validate fail thì trả về 400
    public String handleBindException(BindException e) {
        // Trả về message của lỗi đầu tiên
        String errorMessage = "Request không hợp lệ";
        if (e.getBindingResult().hasErrors())
           e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return errorMessage;
    }

    public ResponseEntity<ErrorResponse> toErrorResponse(String id,
                                                         String error,
                                                         String message,
                                                         HttpStatus status) {
        return toResponseEntity(new ErrorResponse(id, error, message), status);
    }

    public ResponseEntity<ErrorResponse> toErrorResponse(String id,
                                                         String error,
                                                         String message,
                                                         JsonNode description,
                                                         HttpStatus status) {
        return toResponseEntity(new ErrorResponse(id, error, message, description), status);
    }

    @SuppressWarnings("all")
    public ResponseEntity<ErrorResponse> toErrorResponse(ErrorCodeException ex) {
        var errorCode = ex.getErrorCode();
        var errorResp = new ErrorResponse(errorCode.code(), errorCode.message());
        return toResponseEntity(errorResp, errorCode.status());
    }

    public ResponseEntity<ErrorResponse> toErrorResponse(ErrorCode errorCode, Throwable ex) {
        var errorResp = new ErrorResponse(errorCode.code(), ex.getMessage());
        return toResponseEntity(errorResp, errorCode.status());
    }
}
