package com.br.authentication.exception;

import com.br.common.exception.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * author: anct
 * date: 10/9/2022
 * YNWA
 */
@Getter
public enum AuthenticationErrors implements ErrorCode {
    TOO_MANY_OTP_REQUEST("Auth-001", HttpStatus.TOO_MANY_REQUESTS),
    INVALID_OTP("Auth-002", HttpStatus.BAD_REQUEST, "Invalid OTP input."),
    INVALID_OTP_REQUESTED_IP("Auth-003", HttpStatus.BAD_REQUEST, "Invalid requested ip."),
    OTP_EXPIRED("Auth-004", HttpStatus.BAD_REQUEST, "OTP expired."),
    ALREADY_REQUEST_OTP("Auth-005", HttpStatus.BAD_REQUEST, "You already request an otp. Please wait."),
    INVALID_LOGIN("Auth-006", HttpStatus.BAD_REQUEST, "Incorrect username or password"),
    FULLNAME_REQUEST("Auth-007",HttpStatus.NOT_FOUND,"Full name can not be blank"),
    EMAIL_REQUEST("Auth-008",HttpStatus.NOT_FOUND,"Email can not be blank"),
    PASSWORD_REQUEST("Auth-009",HttpStatus.NOT_FOUND,"Password can not be blank"),

    EMAIL_EXISTED("Auth-010",HttpStatus.NOT_FOUND,"Email already exists"),
    PHONE_EXISTED("Auth-014",HttpStatus.NOT_FOUND,"Phone already exists"),
    EMAIL_NOT_CORRECT("Auth-011",HttpStatus.NOT_FOUND,"Incorrect email format, please re-enter"),

    PHONE_REQUEST("Auth-012",HttpStatus.NOT_FOUND,"Phone can not be blank"),

    USER_NOT_EXISTED("Auth-013", HttpStatus.NOT_FOUND, "User not found");

    private final String code;
    private final HttpStatus status;
    private String message;

    AuthenticationErrors(String code,
                         HttpStatus status,
                         String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }

    AuthenticationErrors(String code, HttpStatus status) {
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
