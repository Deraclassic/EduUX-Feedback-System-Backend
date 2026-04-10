package com.dera.eduux_feedback_system.controller;

import com.dera.eduux_feedback_system.dto.response.ApiResponse;
import com.dera.eduux_feedback_system.dto.response.UserResponse;
import com.dera.eduux_feedback_system.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers() {
        return ResponseEntity.ok(ApiResponse.success(userService.getAllUsers(), "Users retrieved successfully"));
    }
}
