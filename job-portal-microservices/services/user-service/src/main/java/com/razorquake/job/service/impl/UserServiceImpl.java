package com.razorquake.job.service.impl;

import com.razorquake.job.domain.UserStatus;
import com.razorquake.job.dto.UserResponse;
import com.razorquake.job.exception.UserNotFoundException;
import com.razorquake.job.mapper.UserMapper;
import com.razorquake.job.model.User;
import com.razorquake.job.payload.UpdateUserRequest;
import com.razorquake.job.repository.UserRepository;
import com.razorquake.job.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("User not found with id: " + id)
        );
    }

    @Override
    public User geUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new UserNotFoundException("User not found with email: " + email)
        );
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserResponse updateProfile(String email, UpdateUserRequest updateUserRequest) {
        User user = geUserByEmail(email);
        if (updateUserRequest.getFullName() != null) {
            user.setFullName(updateUserRequest.getFullName());
        }
        if (updateUserRequest.getPhone() != null) {
            user.setPhone(updateUserRequest.getPhone());
        }
        if (updateUserRequest.getProfilePicture() != null) {
            user.setProfilePicture(updateUserRequest.getProfilePicture());
        }
        return UserMapper.toUserResponse(userRepository.save(user));
    }

    @Override
    public UserResponse suspendUser(Long id) {
        User user = getUserById(id);
        user.setStatus(UserStatus.SUSPENDED);
        user.setSuspendedAt(LocalDateTime.now());
        return UserMapper.toUserResponse(userRepository.save(user));
    }

    @Override
    public UserResponse activateUser(Long id) {
        User user = getUserById(id);
        user.setStatus(UserStatus.ACTIVE);
        return UserMapper.toUserResponse(userRepository.save(user));
    }

    @Override
    public UserResponse deleteUser(Long id) {
        User user = getUserById(id);
        user.setStatus(UserStatus.DELETED);
        user.setDeletedAt(LocalDateTime.now());
        return UserMapper.toUserResponse(userRepository.save(user));
    }
}
