package com.example.brain2build.repository;

import com.example.brain2build.domain.entity.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@TestPropertySource("classpath:application-test.properties")
class RoleRepositoryTest {

    @Autowired
    private RoleRepository roleRepository;

    @Test
    @DisplayName("save + findByNom: should persist and retrieve a role by its unique name")
    void shouldSaveAndFindByNom() {
        Role role = new Role();
        role.setNom("ROLE_WORKER");

        Role saved = roleRepository.save(role);
        assertThat(saved.getId()).isNotNull();

        Optional<Role> found = roleRepository.findByNom("ROLE_WORKER");
        assertThat(found).isPresent();
        assertThat(found.get().getNom()).isEqualTo("ROLE_WORKER");
    }

    @Test
    @DisplayName("unique constraint: saving two roles with the same 'nom' should fail on the second save")
    void shouldRejectDuplicateRoleNames() {
        Role r1 = new Role(); r1.setNom("ROLE_IDEATOR");
        Role r2 = new Role(); r2.setNom("ROLE_IDEATOR");

        roleRepository.saveAndFlush(r1);
        // Flush triggers the DB constraint immediately
        assertThatThrownBy(() -> roleRepository.saveAndFlush(r2))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    @DisplayName("NOT NULL: saving a role with null name should fail")
    void shouldRejectNullName() {
        Role r = new Role(); // nom not set => null
        assertThatThrownBy(() -> roleRepository.saveAndFlush(r))
                .as("Column 'nom' is NOT NULL at DB level")
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    @DisplayName("length ≤ 50: name longer than 50 chars should fail at DB level")
    void shouldEnforceMaxLengthFifty() {
        String tooLong = "X".repeat(51); // 51 chars
        Role r = new Role(); r.setNom(tooLong);

        assertThatThrownBy(() -> roleRepository.saveAndFlush(r))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    @DisplayName("findAll: should list all saved roles")
    void shouldReturnAllSavedRoles() {
        Role a = new Role(); a.setNom("ROLE_A");
        Role b = new Role(); b.setNom("ROLE_B");
        Role c = new Role(); c.setNom("ROLE_C");

        roleRepository.saveAll(List.of(a, b, c));

        List<Role> all = roleRepository.findAll();
        assertThat(all).extracting(Role::getNom)
                .containsExactlyInAnyOrder("ROLE_A", "ROLE_B", "ROLE_C");
    }

    @Nested
    @DisplayName("findByNom edge-cases")
    class FindByNomEdgeCases {
        @Test
        @DisplayName("returns empty when role does not exist")
        void shouldReturnEmptyWhenNotFound() {
            Optional<Role> found = roleRepository.findByNom("ROLE_UNKNOWN");
            assertThat(found).isEmpty();
        }

        @Test
        @DisplayName("case sensitivity behaves as DB collation dictates (usually case-sensitive)")
        void caseSensitivityNote() {
            Role r = new Role(); r.setNom("ROLE_CASE");
            roleRepository.save(r);

            // Usually case-sensitive on PostgreSQL unless you added CITEXT/IC collations
            assertThat(roleRepository.findByNom("role_case")).isEmpty();
            assertThat(roleRepository.findByNom("ROLE_CASE")).isPresent();
        }
    }
}
