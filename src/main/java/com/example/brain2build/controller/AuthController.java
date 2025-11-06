package com.example.brain2build.controller;

import com.example.brain2build.domain.dto.Auth.*;
import com.example.brain2build.service.Auth.AuthService;
import com.example.brain2build.service.Auth.Ideator.IdeatorAuthService;
import com.example.brain2build.service.Auth.Worker.WorkerAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final IdeatorAuthService ideatorAuthService;
    private final WorkerAuthService workerAuthService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register/ideator")
    public ResponseEntity<AuthResponse> registerIdeator(@RequestBody IdeatorRegisterRequest request) {
        return ResponseEntity.ok(ideatorAuthService.registerIdeator(request));
    }

    @PostMapping("/register/worker")
    public ResponseEntity<AuthResponse> registerWorker(@RequestBody WorkerRegisterRequest request) {
        return ResponseEntity.ok(workerAuthService.registerWorker(request));
    }
}
