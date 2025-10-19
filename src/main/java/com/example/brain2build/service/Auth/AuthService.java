package com.example.brain2build.service.Auth;

import com.example.brain2build.domain.dto.Auth.AuthResponse;
import com.example.brain2build.domain.dto.Auth.LoginRequest;
import com.example.brain2build.domain.dto.Auth.RegisterRequest;

public interface AuthService {

    AuthResponse register(RegisterRequest request);

    AuthResponse login(LoginRequest request);
}
