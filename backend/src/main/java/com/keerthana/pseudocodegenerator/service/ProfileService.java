package com.keerthana.pseudocodegenerator.service;

import com.keerthana.pseudocodegenerator.dto.ProfileResponse;

public interface ProfileService {

    ProfileResponse getProfile(String email);
}