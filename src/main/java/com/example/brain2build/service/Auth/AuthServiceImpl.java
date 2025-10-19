package com.example.brain2build.service.Auth;

import com.example.brain2build.config.jwt.JwtProvider;
import com.example.brain2build.domain.dto.Auth.*;
import com.example.brain2build.domain.entity.User;
import com.example.brain2build.mappers.UserMapper;
import com.example.brain2build.repository.RoleRepository;
import com.example.brain2build.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;

    @Override
    public AuthResponse register(RegisterRequest request) {
        User user = userMapper.toEntity(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(Set.of(roleRepository.findByName("ROLE_USER").orElseThrow()));
        userRepository.save(user);

        String token = jwtProvider.generateToken(user);
        return new AuthResponse(token);
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        String token = jwtProvider.generateToken(user);
        return new AuthResponse(token);
    }
}
