package org.example.authservice.service.Auth;

import lombok.RequiredArgsConstructor;
import org.example.authservice.service.Auth.config.JwtService;
import org.example.authservice.domain.dto.Auth.AuthResponse;
import org.example.authservice.domain.dto.Auth.LoginRequest;
import org.example.authservice.domain.dto.Auth.RegisterRequest;
import org.example.authservice.domain.entity.AuthUser;
import org.example.authservice.repositorie.AuthUserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthUserRepository authUserRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        AuthUser authUser = authUserRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        String token = jwtService.generateToken(authUser.getId(), authUser.getRole());
        return new AuthResponse(token);
        // plus tard tu peux mettre userId, role, etc.
    }

    @Override
    public AuthResponse register(RegisterRequest request) {

        if (authUserRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }

        // Déterminer le rôle final stocké dans la DB
        String role;
        if ("WORKER".equalsIgnoreCase(request.getRole())) {
            role = "ROLE_WORKER";
        } else if ("IDEATOR".equalsIgnoreCase(request.getRole())) {
            role = "ROLE_IDEATOR";
        } else {
            throw new IllegalArgumentException("Invalid role: " + request.getRole());
        }

        // Création de l’utilisateur d’auth (PAS Worker/Ideator métier, juste sécurité)
        AuthUser authUser = AuthUser.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                .build();

        authUserRepository.save(authUser);

        // 👉 TODO PLUS TARD :
        // Appeler user-service (via Feign) pour créer :
        // - WorkerProfile si role = WORKER
        // - IdeatorProfile si role = IDEATOR
        // En lui passant les champs spécifiques (bio, domaine, specialite...)

        String token = jwtService.generateToken(authUser.getId(), authUser.getRole());
        return new AuthResponse(token);
    }
}
