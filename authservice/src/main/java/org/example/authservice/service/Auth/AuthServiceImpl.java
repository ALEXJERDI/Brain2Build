package org.example.authservice.service.Auth;

import lombok.RequiredArgsConstructor;
import org.example.authservice.feight.CreateUserProfileRequest;
import org.example.authservice.feight.UserClient;
import org.example.authservice.feight.UserResponse;
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
    private final UserClient userClient;


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

        UserResponse user = userClient.getUserByEmail(request.getEmail());


        String token = jwtService.generateToken(authUser.getId(), authUser.getRole());
        return new AuthResponse(token);
        // plus tard tu peux mettre userId, role, etc.
    }

    @Override
    public AuthResponse register(RegisterRequest request) {

        // 1️⃣ Vérifier email unique dans AuthUser
        if (authUserRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already in use");
        }

        // 2️⃣ Mapper le rôle
        String finalRole;
        if ("WORKER".equalsIgnoreCase(request.getRole())) {
            finalRole = "ROLE_WORKER";
        }
        else if ("IDEATOR".equalsIgnoreCase(request.getRole())) {
            finalRole = "ROLE_IDEATOR";
        }
        else {
            throw new IllegalArgumentException("Invalid role: " + request.getRole());
        }

        // 3️⃣ Créer l’utilisateur AUTH minimal
        AuthUser authUser = AuthUser.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(finalRole)
                .build();

        authUserRepository.save(authUser);

        // 4️⃣ Préparer DTO pour user-service
        CreateUserProfileRequest profileRequest = new CreateUserProfileRequest();
        profileRequest.setAuthUserId(authUser.getId());
        profileRequest.setEmail(request.getEmail());
        profileRequest.setNom(request.getNom());
        profileRequest.setPrenom(request.getPrenom());
        profileRequest.setTelephone(request.getTelephone());
        profileRequest.setRole(request.getRole());

        // Champs IDEATOR
        profileRequest.setBio(request.getBio());

        // Champs WORKER
        profileRequest.setDomaine(request.getDomaine());
        profileRequest.setSpecialite(request.getSpecialite());
        profileRequest.setExperience(request.getExperience());
        profileRequest.setPortfolioUrl(request.getPortfolioUrl());

        // 5️⃣ Appel synchrone à USER-SERVICE via Feign
        UserResponse createdUserProfile;
        try {
            createdUserProfile = userClient.createUserProfile(profileRequest);
        }
        catch (Exception e) {
            throw new RuntimeException("User-service unavailable or error during profile creation");
        }

        // 6️⃣ Génération du JWT
        String token = jwtService.generateToken(authUser.getId(), authUser.getRole());

        return new AuthResponse(token);
    }

}
