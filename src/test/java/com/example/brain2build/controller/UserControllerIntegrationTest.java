package com.example.brain2build.controller;

import com.example.brain2build.domain.dto.User.UserCreateUpdateDto;
import com.example.brain2build.domain.entity.Role;
import com.example.brain2build.domain.entity.Worker;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
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

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Transactional
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @PersistenceContext
    private EntityManager entityManager;

    private Worker savedWorker;

    @BeforeEach
    void resetDatabase() {
        // 🧹 Clean all relevant tables before each test
        entityManager.createNativeQuery(
                "TRUNCATE TABLE user_role, users, roles RESTART IDENTITY CASCADE"
        ).executeUpdate();

        // 🧩 Create base ROLE
        Role role = new Role();
        role.setNom("ROLE_WORKER");
        entityManager.persist(role);

        // 👷 Create a sample Worker (inherits from User)
        Worker worker = new Worker();
        worker.setEmail("worker@brain2build.com");
        worker.setPassword("pass123");
        worker.setNom("Doe");
        worker.setPrenom("John");
        worker.setTelephone("0700000000");
        worker.setRoles(Set.of(role));

        entityManager.persist(worker);
        savedWorker = worker;
    }

    // ============================================================
    // ✅ TEST 1: GET /users - should list all users
    // ============================================================
    @Test
    @DisplayName("Should return list of all users")
    void shouldGetAllUsers() throws Exception {
        var result = mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(result).contains("worker@brain2build.com");
    }

    // ============================================================
    // ✅ TEST 2: PUT /users/{id} - should update user
    // ============================================================
    @Test
    @DisplayName("Should update user details successfully")
    void shouldUpdateUser() throws Exception {
        UserCreateUpdateDto dto = new UserCreateUpdateDto(
                "new@brain2build.com",
                "pass123",
                "NewName",
                "User",
                "0700112233",
                Set.of()
        );

        var response = mockMvc.perform(put("/users/" + savedWorker.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(response).contains("new@brain2build.com");
        assertThat(response).contains("NewName");
    }

    // ============================================================
    // ✅ TEST 3: DELETE /users/{id} - should delete user
    // ============================================================
    @Test
    @DisplayName("Should delete user by ID")
    void shouldDeleteUser() throws Exception {
        mockMvc.perform(delete("/users/" + savedWorker.getId()))
                .andExpect(status().isNoContent());

        var deleted = entityManager.find(Worker.class, savedWorker.getId());
        assertThat(deleted).isNull();
    }
}
