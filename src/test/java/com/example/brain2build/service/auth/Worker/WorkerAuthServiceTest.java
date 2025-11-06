package com.example.brain2build.service.auth.Worker;

import com.example.brain2build.config.JwtProvider;
import com.example.brain2build.domain.dto.Auth.AuthResponse;
import com.example.brain2build.domain.dto.Auth.WorkerRegisterRequest;
import com.example.brain2build.domain.entity.Role;
import com.example.brain2build.domain.entity.Worker;
import com.example.brain2build.repository.RoleRepository;
import com.example.brain2build.repository.WorkerRepository;
import com.example.brain2build.service.Auth.Worker.WorkerAuthServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class WorkerAuthServiceTest {

    @Mock
    private WorkerRepository workerRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private JwtProvider jwtProvider;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private WorkerAuthServiceImpl workerAuthService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // ============================================================
    // ✅ TEST 1 : Register Worker successfully
    // ============================================================
    @Test
    @DisplayName("Should register a Worker and return a JWT token")
    void shouldRegisterWorkerAndReturnToken() {
        // Given
        WorkerRegisterRequest registerRequest = new WorkerRegisterRequest();
        registerRequest.setEmail("mario@brain2build.com");
        registerRequest.setPassword("securepass");
        registerRequest.setNom("Mario");
        registerRequest.setPrenom("Luigi");
        registerRequest.setTelephone("0600000001");
        registerRequest.setDomaine("Engineering");
        registerRequest.setSpecialite("Backend");
        registerRequest.setExperience(5);
        registerRequest.setPortfolioUrl("https://portfolio.com/mario");

        Role role = new Role();
        role.setNom("ROLE_WORKER");

        // Mock dependencies
        when(roleRepository.findByNom("ROLE_WORKER")).thenReturn(Optional.of(role));
        when(passwordEncoder.encode("securepass")).thenReturn("encodedPassword");
        when(jwtProvider.generateToken(any(Worker.class))).thenReturn("mock-jwt-token");
        when(workerRepository.save(any(Worker.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // When
        AuthResponse response = workerAuthService.registerWorker(registerRequest);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getToken()).isEqualTo("mock-jwt-token");

        // Verify interactions
        verify(roleRepository, times(1)).findByNom("ROLE_WORKER");
        verify(passwordEncoder, times(1)).encode("securepass");
        verify(workerRepository, times(1)).save(any(Worker.class));
        verify(jwtProvider, times(1)).generateToken(any(Worker.class));
    }

    // ============================================================
    // ✅ TEST 2 : Missing ROLE_WORKER exception
    // ============================================================
    @Test
    @DisplayName("Should throw exception if ROLE_WORKER not found")
    void shouldThrowExceptionWhenRoleNotFound() {
        // Given
        WorkerRegisterRequest registerRequest = new WorkerRegisterRequest();
        registerRequest.setEmail("fail@brain2build.com");
        registerRequest.setPassword("pass");

        // Mock: role not found in DB
        when(roleRepository.findByNom("ROLE_WORKER")).thenReturn(Optional.empty());

        // When & Then
        assertThatThrownBy(() -> workerAuthService.registerWorker(registerRequest))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("No value present");

        // Verify nothing was saved or token generated
        verify(workerRepository, never()).save(any());
        verify(jwtProvider, never()).generateToken(any());
    }
}
