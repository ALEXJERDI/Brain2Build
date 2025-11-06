package com.example.brain2build.service.Auth.Ideator;

import com.example.brain2build.domain.dto.Auth.AuthResponse;
import com.example.brain2build.domain.dto.Auth.IdeatorRegisterRequest;

public interface IdeatorAuthService {
    AuthResponse registerIdeator(IdeatorRegisterRequest request);
}

