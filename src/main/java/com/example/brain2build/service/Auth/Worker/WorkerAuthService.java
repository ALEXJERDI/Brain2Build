package com.example.brain2build.service.Auth.Worker;

import com.example.brain2build.domain.dto.Auth.AuthResponse;
import com.example.brain2build.domain.dto.Auth.WorkerRegisterRequest;

public interface WorkerAuthService {
    AuthResponse registerWorker(WorkerRegisterRequest request);
}

