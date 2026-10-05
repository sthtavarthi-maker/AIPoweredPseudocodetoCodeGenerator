package com.keerthana.pseudocodegenerator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ProfileResponse {

    private Long id;

    private String fullName;

    private String email;

    private String favoriteLanguage;

    private String role;

    private LocalDateTime createdAt;
}