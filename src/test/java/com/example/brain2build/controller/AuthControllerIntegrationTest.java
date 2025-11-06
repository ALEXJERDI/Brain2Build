package com.example.brain2build.controller;

import com.example.brain2build.domain.dto.Auth.*;
import com.example.brain2build.domain.entity.Role;
import com.example.brain2build.repository.RoleRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test") // loads application-test.properties
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class AuthControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RoleRepository roleRepository;

    @BeforeEach
    void setup() {
        // Ensure roles exist before registration endpoints are called
        if (roleRepository.findByNom("ROLE_IDEATOR").isEmpty()) {
            Role role = new Role();
            role.setNom("ROLE_IDEATOR");
            roleRepository.save(role);
        }
        if (roleRepository.findByNom("ROLE_WORKER").isEmpty()) {
            Role role = new Role();
            role.setNom("ROLE_WORKER");
            roleRepository.save(role);
        }
    }

    // ============================================================
    // ✅ TEST 1: Login endpoint (should fail if user not registered)
    // ============================================================
    @Test
    @DisplayName("Login should fail for unregistered user")
    void loginShouldFailForUnknownUser() throws Exception {
        LoginRequest loginRequest = new LoginRequest("unknown@brain2build.com", "wrongpass");

        mockMvc.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isUnauthorized())       // 401
                .andExpect(content().string("Invalid email or password"));
    }


    // ============================================================
    // ✅ TEST 2: Register ideator successfully
    // ============================================================
    @Test
    @DisplayName("Should register ideator and return JWT token")
    void shouldRegisterIdeatorAndReturnToken() throws Exception {
        IdeatorRegisterRequest request = new IdeatorRegisterRequest(
                "elon@brain2build.com",
                "password123",
                "Elon",
                "Musk",
                "0600000000",
                "Building the future"
        );

        var response = mockMvc.perform(post("/auth/register/ideator")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andReturn()
                .getResponse()
                .getContentAsString();

        AuthResponse authResponse = objectMapper.readValue(response, AuthResponse.class);
        assertThat(authResponse.getToken()).isNotNull();
    }

    // ============================================================
    // ✅ TEST 3: Register worker successfully
    // ============================================================
    @Test
    @DisplayName("Should register worker and return JWT token")
    void shouldRegisterWorkerAndReturnToken() throws Exception {
        WorkerRegisterRequest request = new WorkerRegisterRequest(
                "worker@brain2build.com",
                "password123",
                "John",
                "Doe",
                "0700000000",
                "Engineering",
                "Backend",
                3,
                "https://portfolio.com/johndoe"
        );

        var response = mockMvc.perform(post("/auth/register/worker")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andReturn()
                .getResponse()
                .getContentAsString();

        AuthResponse authResponse = objectMapper.readValue(response, AuthResponse.class);
        assertThat(authResponse.getToken()).isNotEmpty();
    }
}
