package com.example.brain2build.service.auth.Ideator;

import com.example.brain2build.config.JwtProvider;
import com.example.brain2build.domain.dto.Auth.AuthResponse;
import com.example.brain2build.domain.dto.Auth.IdeatorRegisterRequest;
import com.example.brain2build.domain.entity.Ideator;
import com.example.brain2build.domain.entity.Role;
import com.example.brain2build.repository.IdeatorRepository;
import com.example.brain2build.repository.RoleRepository;
import com.example.brain2build.service.Auth.Ideator.IdeatorAuthServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class IdeatorAuthServiceTest {

    @Mock
    private IdeatorRepository ideatorRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private JwtProvider jwtProvider;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private IdeatorAuthServiceImpl ideatorAuthService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should register an Ideator and return a token")
    void shouldRegisterIdeatorAndReturnToken() {
        // Given
        IdeatorRegisterRequest registerRequest = new IdeatorRegisterRequest();
        registerRequest.setEmail("elonmusk@brain2build.com");
        registerRequest.setPassword("password123");
        registerRequest.setNom("Musk");
        registerRequest.setPrenom("Elon");
        registerRequest.setTelephone("0700000000");
        registerRequest.setBio("Tech entrepreneur");

        Role role = new Role();
        role.setNom("ROLE_IDEATOR");

        // Mock dependencies
        when(roleRepository.findByNom("ROLE_IDEATOR")).thenReturn(Optional.of(role));
        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        when(jwtProvider.generateToken(any(Ideator.class))).thenReturn("mock-jwt-token"); // ✅ key fix
        when(ideatorRepository.save(any(Ideator.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        AuthResponse response = ideatorAuthService.registerIdeator(registerRequest);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getToken()).isEqualTo("mock-jwt-token");

        // Verify behavior
        verify(roleRepository, times(1)).findByNom("ROLE_IDEATOR");
        verify(passwordEncoder, times(1)).encode("password123");
        verify(jwtProvider, times(1)).generateToken(any(Ideator.class));
        verify(ideatorRepository, times(1)).save(any(Ideator.class));
    }
}
