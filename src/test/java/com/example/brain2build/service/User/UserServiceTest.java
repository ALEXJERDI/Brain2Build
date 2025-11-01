package com.example.brain2build.service.User;

import com.example.brain2build.domain.dto.Role.RoleDto;
import com.example.brain2build.domain.dto.User.UserCreateUpdateDto;
import com.example.brain2build.domain.dto.User.UserReadDto;
import com.example.brain2build.domain.entity.Role;
import com.example.brain2build.domain.entity.Worker;
import com.example.brain2build.repository.UserRepository;
import com.example.brain2build.service.user.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    private AutoCloseable closeable; // For cleaner Mockito management

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    // ============================================================
    // ✅ TEST 1: Get all users
    // ============================================================
    @Test
    @DisplayName("Should return all users as DTOs")
    void shouldReturnAllUsers() {
        // Given
        Role role = new Role();
        role.setId(15L);
        role.setNom("ROLE_USER");

        Worker user = new Worker();
        user.setId(1L);
        user.setEmail("test@brain2build.com");
        user.setNom("Doe");
        user.setPrenom("John");
        user.setTelephone("0600000000");
        user.setRoles(Set.of(role));

        when(userRepository.findAll()).thenReturn(List.of(user));

        // When
        Set<UserReadDto> result = userService.getAllUsers();

        // Then
        assertThat(result).hasSize(1);
        UserReadDto dto = result.iterator().next();
        assertThat(dto.getEmail()).isEqualTo("test@brain2build.com");
        assertThat(dto.getRoles()).extracting(RoleDto::getNom).containsExactly("ROLE_USER");

        verify(userRepository, times(1)).findAll();
    }

    // ============================================================
    // ✅ TEST 2: Update user
    // ============================================================
    @Test
    @DisplayName("Should update user details successfully")
    void shouldUpdateUser() {
        // Given: existing user in DB
        Worker existingUser = new Worker();
        existingUser.setId(1L);
        existingUser.setEmail("old@brain2build.com");
        existingUser.setNom("Old");
        existingUser.setPrenom("Name");
        existingUser.setTelephone("0500000000");

        // Because UserCreateUpdateDto is @Value (immutable),
        // we use the constructor instead of setters.
        UserCreateUpdateDto updateDto = new UserCreateUpdateDto(
                "new@brain2build.com",
                "secret123", // password required by validation
                "New",
                "User",
                "0700000000",
                Set.of(1L)
        );

        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));

        // When
        UserReadDto updatedUser = userService.updateUser(1L, updateDto);

        // Then
        assertThat(updatedUser.getEmail()).isEqualTo("new@brain2build.com");
        assertThat(updatedUser.getNom()).isEqualTo("New");
        assertThat(updatedUser.getPrenom()).isEqualTo("User");
        assertThat(updatedUser.getTelephone()).isEqualTo("0700000000");

        verify(userRepository, times(1)).findById(1L);
    }

    // ============================================================
    // ✅ TEST 3: Throw exception if user not found
    // ============================================================
    @Test
    @DisplayName("Should throw exception when user not found during update")
    void shouldThrowWhenUserNotFound() {
        // Given
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        UserCreateUpdateDto updateDto = new UserCreateUpdateDto(
                "notfound@brain2build.com",
                "password123",
                "John",
                "Doe",
                "0700000000",
                Set.of()
        );

        // When / Then
        assertThatThrownBy(() -> userService.updateUser(1L, updateDto))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("User not found");

        verify(userRepository, times(1)).findById(1L);
    }

    // ============================================================
    // ✅ TEST 4: Delete user
    // ============================================================
    @Test
    @DisplayName("Should delete user by ID")
    void shouldDeleteUser() {
        // Given
        Long userId = 42L;

        // When
        userService.deleteUser(userId);

        // Then
        verify(userRepository, times(1)).deleteById(userId);
    }
}
