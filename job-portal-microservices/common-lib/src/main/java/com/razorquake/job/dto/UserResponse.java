package com.razorquake.job.dto;

import com.razorquake.job.domain.UserRole;
import com.razorquake.job.domain.UserStatus;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UserResponse {
    private Long id;
    private String fullName;
    private  String email;
    private String phone;
    private String profilePicture;
    private UserRole role;
    private UserStatus status;
    private LocalDateTime lastLogin;
    private LocalDateTime createdAt;
}
