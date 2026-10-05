package com.keerthana.pseudocodegenerator.service.impl;

import org.springframework.stereotype.Service;

import com.keerthana.pseudocodegenerator.dto.ProfileResponse;
import com.keerthana.pseudocodegenerator.entity.User;
import com.keerthana.pseudocodegenerator.repository.UserRepository;
import com.keerthana.pseudocodegenerator.service.ProfileService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {

    private final UserRepository userRepository;

    @Override
    public ProfileResponse getProfile(String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        return new ProfileResponse(
                user.getId(),
                user.getFullName(),
                user.getEmail(),
                user.getFavoriteLanguage(),
                user.getRole(),
                user.getCreatedAt()
        );
    }
}