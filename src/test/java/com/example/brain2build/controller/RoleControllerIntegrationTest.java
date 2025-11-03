package com.example.brain2build.controller;

import com.example.brain2build.domain.dto.Role.RoleDto;
import com.example.brain2build.domain.entity.Role;
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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc(addFilters = false)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Transactional
class RoleControllerIntegrationTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private ObjectMapper objectMapper;
    @PersistenceContext private EntityManager entityManager;

    @BeforeEach
    void resetDatabase() {
        // 🧹 Clean roles before each test
        entityManager.createNativeQuery("TRUNCATE TABLE user_role, roles RESTART IDENTITY CASCADE").executeUpdate();
    }

    // ============================================================
    // ✅ TEST 1: POST /roles - create a role
    // ============================================================
    @Test
    @DisplayName("Should create a new role successfully")
    void shouldCreateRole() throws Exception {
        RoleDto dto = new RoleDto(null, "ROLE_MANAGER");

        var response = mockMvc.perform(post("/roles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom").value("ROLE_MANAGER"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        RoleDto created = objectMapper.readValue(response, RoleDto.class);
        Role persisted = entityManager.find(Role.class, created.getId());

        assertThat(persisted).isNotNull();
        assertThat(persisted.getNom()).isEqualTo("ROLE_MANAGER");
    }

    // ============================================================
    // ✅ TEST 2: GET /roles - list all roles
    // ============================================================
    @Test
    @DisplayName("Should list all roles")
    void shouldListAllRoles() throws Exception {
        Role roleTester = new Role();
        roleTester.setNom("ROLE_TESTER");

        entityManager.persist(roleTester);
        Role roleAdmin = new Role();
        roleAdmin.setNom("ROLE_ADMIN");
        entityManager.persist(roleAdmin);

        var result = mockMvc.perform(get("/roles"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<RoleDto> roles = List.of(objectMapper.readValue(result, RoleDto[].class));

        assertThat(roles).extracting(RoleDto::getNom)
                .containsExactlyInAnyOrder("ROLE_TESTER", "ROLE_ADMIN");
    }
}
