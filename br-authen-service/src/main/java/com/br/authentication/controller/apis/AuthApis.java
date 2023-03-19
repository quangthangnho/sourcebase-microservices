package com.br.authentication.controller.apis;

import com.br.authentication.dto.LogingRequestDto;
import com.br.authentication.dto.RegisterDto;
import com.br.authentication.model.response.AuthResp;
import com.br.authentication.model.response.UserResp;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RequestMapping("/v1")
public interface AuthApis {

    @Operation(summary = "Login")
    @PostMapping("/login")
    AuthResp authenticateUser(@RequestBody @Validated LogingRequestDto loginRequest);

    @Operation(summary = "Logout")
    @GetMapping("/logout")
    AuthResp logout(Authentication authentication);

    @Operation(summary = "Register")
    @PostMapping(path = "/register")
    AuthResp register(@RequestBody @Validated RegisterDto object);

//    @Operation(summary = "reset password a user")
//    @PutMapping(path = "/reset-password/{id}")
//    ResponseEntity<?> resetPassword(@RequestBody String password, @PathVariable("id") Long id);

    @Operation(summary = "Get user detail by id")
    @GetMapping(path = "/user/{id}")
    public UserResp getDetail(@PathVariable("id") Long id);
}
