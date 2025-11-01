package com.example.brain2build.service.auth;

import com.example.brain2build.domain.dto.Auth.AuthResponse;
import com.example.brain2build.domain.dto.Auth.LoginRequest;
import com.example.brain2build.domain.entity.Ideator;  // Use Ideator instead of User
import com.example.brain2build.repository.UserRepository;
import com.example.brain2build.config.JwtProvider;
import com.example.brain2build.service.Auth.AuthServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtProvider jwtProvider;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthServiceImpl authService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // ============================================================
    // ✅ TEST 1 : Login Success
    // ============================================================
    @Test
    @DisplayName("Should login and return a token on successful authentication")
    void shouldLoginAndReturnToken() {
        // Given
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("test@brain2build.com");
        loginRequest.setPassword("password123");

        Ideator ideator = new Ideator();  // Use Ideator instead of User
        ideator.setEmail("test@brain2build.com");
        ideator.setPassword("password123");  // In real life, it would be encoded

        Authentication authentication = mock(Authentication.class);

        // Mock the behavior of authentication manager, user repository, and JWT provider
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(userRepository.findByEmail("test@brain2build.com")).thenReturn(java.util.Optional.of(ideator));
        when(jwtProvider.generateToken(ideator)).thenReturn("mock-jwt-token");

        // When
        AuthResponse response = authService.login(loginRequest);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getToken()).isEqualTo("mock-jwt-token");

        // Verify that methods are called as expected
        verify(authenticationManager, times(1))
                .authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository, times(1)).findByEmail("test@brain2build.com");
        verify(jwtProvider, times(1)).generateToken(ideator);
    }

    // ============================================================
    // ✅ TEST 2 : Login Failure (Invalid credentials)
    // ============================================================
    @Test
    @DisplayName("Should throw an exception when credentials are invalid")
    void shouldThrowExceptionWhenInvalidCredentials() {
        // Given
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("test@brain2build.com");
        loginRequest.setPassword("incorrectpassword");  // Fixed typo

        // Mock the behavior of user repository to return empty when email is incorrect
        when(userRepository.findByEmail("test@brain2build.com"))
                .thenReturn(java.util.Optional.empty());

        // When & Then
        assertThatThrownBy(() -> authService.login(loginRequest))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Invalid credentials");

        // Verify that the repository was called
        verify(userRepository, times(1)).findByEmail("test@brain2build.com");
    }


}
