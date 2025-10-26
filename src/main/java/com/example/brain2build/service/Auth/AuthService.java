package com.example.brain2build.service.Auth;

import com.example.brain2build.domain.dto.Auth.*;

public interface AuthService {
    AuthResponse login(LoginRequest request);
}
