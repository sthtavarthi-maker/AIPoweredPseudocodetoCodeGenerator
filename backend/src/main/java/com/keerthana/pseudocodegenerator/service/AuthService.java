package com.keerthana.pseudocodegenerator.service;

import com.keerthana.pseudocodegenerator.dto.LoginRequest;
import com.keerthana.pseudocodegenerator.dto.LoginResponse;
import com.keerthana.pseudocodegenerator.dto.RegisterRequest;

public interface AuthService {

    void register(RegisterRequest request);

    LoginResponse login(LoginRequest request);

}