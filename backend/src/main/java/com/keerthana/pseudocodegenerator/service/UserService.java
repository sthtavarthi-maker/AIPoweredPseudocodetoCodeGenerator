package com.keerthana.pseudocodegenerator.service;

import com.keerthana.pseudocodegenerator.dto.LoginRequest;
import com.keerthana.pseudocodegenerator.dto.LoginResponse;
import com.keerthana.pseudocodegenerator.dto.RegisterRequest;
import com.keerthana.pseudocodegenerator.entity.User;

public interface UserService {

    User register(RegisterRequest request);

    LoginResponse login(LoginRequest request);

}