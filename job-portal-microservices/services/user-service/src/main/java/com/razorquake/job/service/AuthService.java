package com.razorquake.job.service;

import com.razorquake.job.payload.AuthResponse;
import com.razorquake.job.payload.LoginRequest;
import com.razorquake.job.payload.SignUpRequest;
import org.springframework.stereotype.Service;

@Service
public interface AuthService {

    AuthResponse signup(SignUpRequest request);

    AuthResponse login(LoginRequest request);
}
