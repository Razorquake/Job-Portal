package com.razorquake.job.service.impl;

import com.razorquake.job.domain.UserRole;
import com.razorquake.job.domain.UserStatus;
import com.razorquake.job.exception.EmailAlreadyExistsException;
import com.razorquake.job.mapper.UserMapper;
import com.razorquake.job.model.User;
import com.razorquake.job.payload.AuthResponse;
import com.razorquake.job.payload.LoginRequest;
import com.razorquake.job.payload.SignUpRequest;
import com.razorquake.job.repository.UserRepository;
import com.razorquake.job.security.JwtUtils;
import com.razorquake.job.security.UserDetailsImpl;
import com.razorquake.job.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    @Override
    public AuthResponse signup(SignUpRequest request) {
        if (userRepository.existsByEmail(request.getEmail()))
            throw new EmailAlreadyExistsException("Email already exists");
        if (request.getRole() == UserRole.ROLE_ADMIN)
            throw new IllegalArgumentException("Admin role is not allowed");
        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .phone(request.getPhone())
                .lastLogin(LocalDateTime.now())
                .status(UserStatus.ACTIVE)
                .build();

        User savedUser = userRepository.save(user);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String jwt = jwtUtils.generateToken(userDetails);
        return AuthResponse.builder()
                .title("Welcome " + savedUser.getFullName())
                .message("You have successfully signed up")
                .jwt(jwt)
                .user(UserMapper.toUserResponse(savedUser))
                .build();
    }

    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        // First check if user exists and is enabled
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + loginRequest.getEmail()));

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
        // Set the authenticated user in the SecurityContext
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String jwt = jwtUtils.generateToken(userDetails);



        return AuthResponse.builder()
                .title("Welcome " + user.getFullName())
                .message("You have successfully logged in")
                .jwt(jwt)
                .user(UserMapper.toUserResponse(user))
                .build();
    }
}
