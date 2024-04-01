package com.murat.userservice.controllers;

import com.murat.userservice.payloads.LoginReqDto;
import com.murat.userservice.services.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("login")
    public String login(@RequestBody @Valid LoginReqDto loginReqDto) {
        return authService.loginUser(loginReqDto);
    }
}
