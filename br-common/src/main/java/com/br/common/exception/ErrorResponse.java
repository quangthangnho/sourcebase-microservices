package com.br.common.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.RandomStringUtils;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    private String id;
    private String error;
    private String message;
    private JsonNode description;

    public ErrorResponse(String error, String message) {
        this.id = RandomStringUtils.randomAlphabetic(6);
        this.error = error;
        this.message = message;
    }

    public ErrorResponse(String id, String error, String message) {
        this.id = id;
        this.error = error;
        this.message = message;
    }
}