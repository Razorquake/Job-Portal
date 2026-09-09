package com.razorquake.job.service;

import com.razorquake.job.dto.UserResponse;
import com.razorquake.job.model.User;
import com.razorquake.job.payload.UpdateUserRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    User getUserById(Long id);

    User geUserByEmail(String email);

    List<User> getAllUsers();

    UserResponse updateProfile(String email, UpdateUserRequest updateUserRequest);

    UserResponse suspendUser(Long id);

    UserResponse activateUser(Long id);

    UserResponse deleteUser(Long id);
}
