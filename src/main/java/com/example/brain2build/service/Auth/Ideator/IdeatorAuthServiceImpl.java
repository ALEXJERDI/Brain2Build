package com.example.brain2build.service.Auth.Ideator;

import com.example.brain2build.config.JwtProvider;
import com.example.brain2build.domain.dto.Auth.AuthResponse;
import com.example.brain2build.domain.dto.Auth.IdeatorRegisterRequest;
import com.example.brain2build.domain.entity.Ideator;
import com.example.brain2build.repository.RoleRepository;
import com.example.brain2build.repository.IdeatorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class IdeatorAuthServiceImpl implements IdeatorAuthService {
    private final IdeatorRepository ideatorRepository;
    private final RoleRepository roleRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponse registerIdeator(IdeatorRegisterRequest request) {
        Ideator ideator = new Ideator();
        ideator.setEmail(request.getEmail());
        ideator.setPassword(passwordEncoder.encode(request.getPassword()));
        ideator.setNom(request.getNom());
        ideator.setPrenom(request.getPrenom());
        ideator.setTelephone(request.getTelephone());
        ideator.setBio(request.getBio());
        ideator.setIdeaCount(0);
        ideator.setRoles(Set.of(roleRepository.findByNom("ROLE_IDEATOR").orElseThrow()));

        ideatorRepository.save(ideator);

        String token = jwtProvider.generateToken(ideator);
        return new AuthResponse(token);
    }
}

