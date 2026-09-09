package com.razorquake.job.payload;

import com.razorquake.job.dto.UserResponse;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthResponse {
    private String jwt;
    private String title;
    private String message;
    private UserResponse user;
}
