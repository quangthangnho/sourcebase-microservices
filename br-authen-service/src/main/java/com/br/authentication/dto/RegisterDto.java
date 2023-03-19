package com.br.authentication.dto;


import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

@Getter
@Setter
public class RegisterDto {
    @NotEmpty(message = "Phone can not be blank")
    String phone;
    @NotEmpty(message = "Full name can not be blank")
    String fullName;
    @NotEmpty(message = "Email can not be blank")
    String email;
    @NotEmpty(message = "Password can not be blank")
    @Min(value = 8, message = "Password must be 8 characters or more")
    String password;
}
