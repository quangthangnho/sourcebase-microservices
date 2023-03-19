package com.br.authentication.controller;

import com.br.authentication.controller.apis.AuthApis;
import com.br.authentication.exception.GlobalExceptionHandler;
import com.br.authentication.model.response.AuthResp;
import com.br.authentication.dto.LogingRequestDto;
import com.br.authentication.dto.RegisterDto;
import com.br.authentication.facade.AuthFacade;
import com.br.authentication.model.response.UserResp;
import com.br.authentication.service.AuthService;
import com.br.common.controller.BaseController;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class AuthController extends BaseController implements AuthApis {
    private final AuthFacade authFacade;
    @Autowired
    AuthService authService;

    @Autowired
    GlobalExceptionHandler globalExceptionHandler;

    @Override
    public AuthResp authenticateUser(@RequestBody @Validated LogingRequestDto loginRequest) {
            return AuthResp.of(authService.authenticateUser(loginRequest));
    }

    @Override
    public AuthResp logout(Authentication authentication) {

            authService.logoutUser(authentication);
            return AuthResp.of("Log out successfully!");
    }
    @Override
    public AuthResp register(@RequestBody @Valid RegisterDto object) {
        return authFacade.register(object);
    }

//    @Override
//    public ResponseEntity<?> resetPassword(@RequestBody String password,@PathVariable("id") Long id) {
//            authFacade.changePassword(password,id);
//            return toResponseEntity("Change password successfully");
//    }

    @Override
    public UserResp getDetail(@PathVariable("id") Long id) {
        return authFacade.getUserById(id);
    }
}
