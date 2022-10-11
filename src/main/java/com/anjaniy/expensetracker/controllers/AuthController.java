package com.anjaniy.expensetracker.controllers;

import com.anjaniy.expensetracker.dto.LoginRequest;
import com.anjaniy.expensetracker.dto.LoginResponse;
import com.anjaniy.expensetracker.dto.RefreshTokenRequest;
import com.anjaniy.expensetracker.dto.RegisterRequest;
import com.anjaniy.expensetracker.services.AuthService;
import com.anjaniy.expensetracker.services.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private RefreshTokenService refreshTokenService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public String register(@Valid @RequestBody RegisterRequest registerRequest){
        authService.register(registerRequest);
        return "User Registration Successful!";
    }

    @PostMapping("/login")
    @ResponseStatus(OK)
    public LoginResponse login(@RequestBody LoginRequest loginRequest){
        return authService.login(loginRequest);
    }

    @PostMapping("/refresh/token")
    @ResponseStatus(OK)
    public LoginResponse refreshTokens(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        return authService.refreshToken(refreshTokenRequest);
    }

    @PostMapping("/logout")
    @ResponseStatus(OK)
    public String logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
        return "Refresh Token Deleted Successfully!!";
    }
}
