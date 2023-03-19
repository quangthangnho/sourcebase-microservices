package com.br.authentication.dto;

import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.NotEmpty;

@Getter
@Setter
public class LogingRequestDto {
    @NotEmpty(message = "Phone can not be blank")
    public String phone;
    @NotEmpty(message = "Password can not be blank")
    public String password;
}
