package com.keerthana.pseudocodegenerator.service.impl;

import com.keerthana.pseudocodegenerator.dto.LoginRequest;
import com.keerthana.pseudocodegenerator.dto.LoginResponse;
import com.keerthana.pseudocodegenerator.dto.RegisterRequest;
import com.keerthana.pseudocodegenerator.entity.User;
import com.keerthana.pseudocodegenerator.repository.UserRepository;
import com.keerthana.pseudocodegenerator.security.JwtService;
import com.keerthana.pseudocodegenerator.service.AuthService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Override
    public void register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists.");
        }

        User user = User.builder()
                .fullName(request.getFullName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .favoriteLanguage(request.getFavoriteLanguage())
                .role("USER")
                .createdAt(LocalDateTime.now())
                .build();

        userRepository.save(user);
    }

    @Override
    public LoginResponse login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid Email"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid Password");
        }

        String token = jwtService.generateToken(user.getEmail());

        return new LoginResponse(
                token,
                "Login Successful"
        );
    }
}