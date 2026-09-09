package com.razorquake.job.payload;

import com.razorquake.job.domain.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SignUpRequest {

    @NotBlank(message = "Full name cannot be blank")
    private String fullName;

    @Email(message = "Invalid email format")
    @NotBlank(message = "Email cannot be blank")
    private String email;

    @NotBlank(message = "Password cannot be blank")
    private String password;

    private String phone;

    @NotNull(message = "Role cannot be null")
    private UserRole role;
}
