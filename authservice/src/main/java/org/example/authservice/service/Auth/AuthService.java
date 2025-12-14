package org.example.authservice.service.Auth;

import org.example.authservice.domain.dto.Auth.*;

public interface AuthService {

    AuthResponse login(LoginRequest request);

    AuthResponse
    register(RegisterRequest request);
}
