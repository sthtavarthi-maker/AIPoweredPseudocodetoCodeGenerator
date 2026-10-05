package com.keerthana.pseudocodegenerator.service.impl;

import com.keerthana.pseudocodegenerator.dto.LoginRequest;
import com.keerthana.pseudocodegenerator.dto.LoginResponse;
import com.keerthana.pseudocodegenerator.dto.RegisterRequest;
import com.keerthana.pseudocodegenerator.entity.User;
import com.keerthana.pseudocodegenerator.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public User register(RegisterRequest request) {
        return null;
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        return null;
    }
}