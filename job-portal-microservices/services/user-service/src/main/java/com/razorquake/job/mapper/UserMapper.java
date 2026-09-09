package com.razorquake.job.mapper;

import com.razorquake.job.dto.UserResponse;
import com.razorquake.job.model.User;

public class UserMapper {

    public static UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .createdAt(user.getCreatedAt())
                .lastLogin(user.getLastLogin())
                .profilePicture(user.getProfilePicture())
                .role(user.getRole())
                .status(user.getStatus())
                .build();
    }
}
