package com.razorquake.job.controller;

import com.razorquake.job.dto.UserResponse;
import com.razorquake.job.mapper.UserMapper;
import com.razorquake.job.payload.UpdateUserRequest;
import com.razorquake.job.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PutMapping("/profile")
    public ResponseEntity<UserResponse> updateProfile(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody UpdateUserRequest updateUserRequest
    ) {
        return ResponseEntity.ok(
                userService.updateProfile(userDetails.getUsername(), updateUserRequest)
        );
    }

    @GetMapping("/profile")
    public ResponseEntity<UserResponse> getProfile(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        return ResponseEntity.ok(
                UserMapper.toUserResponse(
                        userService.geUserByEmail(userDetails.getUsername())
                )
        );
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(
                userService.getAllUsers()
                        .stream()
                        .map(UserMapper::toUserResponse)
                        .toList()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                UserMapper.toUserResponse(
                        userService.getUserById(id)
                )
        );
    }

    @PatchMapping("/{id}/suspend")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserResponse> suspendUser(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                userService.suspendUser(id)
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}/activate")
    public ResponseEntity<UserResponse> activateUser(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                userService.activateUser(id)
        );
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/{id}/delete")
    public ResponseEntity<UserResponse> deleteUser(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(
                userService.deleteUser(id)
        );
    }

}
