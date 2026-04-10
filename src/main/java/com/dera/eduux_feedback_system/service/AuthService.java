package com.dera.eduux_feedback_system.service;

import com.dera.eduux_feedback_system.dto.request.LoginRequest;
import com.dera.eduux_feedback_system.dto.request.RegisterRequest;
import com.dera.eduux_feedback_system.dto.response.ApiResponse;
import com.dera.eduux_feedback_system.dto.response.AuthResponse;
import com.dera.eduux_feedback_system.entity.User;
import com.dera.eduux_feedback_system.exception.DuplicateResourceException;
import com.dera.eduux_feedback_system.exception.UnauthorizedException;
import com.dera.eduux_feedback_system.repository.UserRepository;
import com.dera.eduux_feedback_system.security.JwtUtil;
import com.dera.eduux_feedback_system.security.TokenBlacklistService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final TokenBlacklistService tokenBlacklistService;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new DuplicateResourceException("Email already registered");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        userRepository.save(user);

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());

        return AuthResponse.builder()
                .token(token)
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UnauthorizedException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new UnauthorizedException("Invalid email or password");
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole().name());

        return AuthResponse.builder()
                .token(token)
                .name(user.getName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }

    public ApiResponse<Void> logout(String authHeader) {
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new UnauthorizedException("No token provided");
        }
        String token = authHeader.substring(7);
        if (!jwtUtil.isTokenValid(token)) {
            throw new UnauthorizedException("Token is already invalid");
        }
        tokenBlacklistService.blacklist(token);
        return ApiResponse.success(null, "Logged out successfully");
    }
}