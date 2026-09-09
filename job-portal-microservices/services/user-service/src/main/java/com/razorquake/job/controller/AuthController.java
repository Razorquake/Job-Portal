package com.razorquake.job.controller;

import com.razorquake.job.payload.AuthResponse;
import com.razorquake.job.payload.LoginRequest;
import com.razorquake.job.payload.SignUpRequest;
import com.razorquake.job.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public AuthResponse signup(
            @RequestBody @Valid SignUpRequest signupRequest
    ) {
        return authService.signup(signupRequest);
    }

    @PostMapping("/login")
    public AuthResponse login(
            @RequestBody @Valid LoginRequest loginRequest
    ) {
        return authService.login(loginRequest);
    }

}
