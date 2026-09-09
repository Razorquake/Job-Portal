package com.razorquake.job.payload;

import lombok.Data;

@Data
public class UpdateUserRequest {

    private String fullName;

    private String phone;

    private String profilePicture;
}
