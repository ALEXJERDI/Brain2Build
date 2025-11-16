package org.example.authservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.authservice.domain.dto.Auth.AuthResponse;
import org.example.authservice.domain.dto.Auth.LoginRequest;
import org.example.authservice.domain.dto.Auth.RegisterRequest;
import org.example.authservice.service.Auth.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/login/admin")
    public ResponseEntity<AuthResponse> loginAdmin(@RequestBody LoginRequest request) {
        // pour l’instant, même traitement que login normal
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }
}
