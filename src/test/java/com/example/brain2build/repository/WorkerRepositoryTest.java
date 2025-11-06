package com.example.brain2build.repository;

import com.example.brain2build.domain.entity.Role;
import com.example.brain2build.domain.entity.Worker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@TestPropertySource("classpath:application-test.properties")
@Rollback(false) // keep data after test
class WorkerRepositoryTest {

    @Autowired
    private WorkerRepository workerRepository;

    @Autowired
    private RoleRepository roleRepository;



    // ============================================================
    // ✅ TEST 1 : Save + Find by Email
    // ============================================================
    @Test
    @DisplayName("Should save and retrieve a worker by email")
    void shouldSaveAndFindWorkerByEmail() {
        Worker w = new Worker();
        w.setEmail("anashalim01@brain2build.com");
        w.setPassword("secret123");
        w.setNom("Doe");
        w.setPrenom("John");
        w.setTelephone("0700000001");
        w.setDomaine("IT");
        w.setSpecialite("Backend");
        w.setExperience(3);
        w.setPortfolioUrl("https://portfolio.com/john");

        Worker saved = workerRepository.save(w);
        assertThat(saved.getId()).isNotNull();

        Optional<Worker> found = workerRepository.findByEmail("anashalim01@brain2build.com");
        assertThat(found).isPresent();
        assertThat(found.get().getNom()).isEqualTo("Doe");
        assertThat(found.get().getSpecialite()).isEqualTo("Backend");
    }

    // ============================================================
    // ✅ TEST 2 : Delete Worker
    // ============================================================
    @Test
    @DisplayName("Should delete a worker by ID")
    void shouldDeleteWorker() {
        Worker w = new Worker();
        w.setEmail("delete@brain2build.com");
        w.setPassword("pass");
        w.setNom("Delete");
        w.setPrenom("User");
        w.setDomaine("AI");
        w.setSpecialite("ML");
        workerRepository.save(w);

        Long id = w.getId();
        workerRepository.deleteById(id);

        Optional<Worker> found = workerRepository.findById(id);
        assertThat(found).isEmpty();
    }

    // ============================================================
    // ✅ TEST 3 : Constraint NOT NULL (isolated from transaction rollback)
    // ============================================================
    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @DisplayName("Should reject null email or password (NOT NULL constraint)")
    void shouldRejectNullEmailOrPassword() {
        Worker w = new Worker();
        w.setNom("Invalid");
        w.setPrenom("User");
        w.setDomaine("Design");
        w.setSpecialite("UI");

        assertThatThrownBy(() -> workerRepository.saveAndFlush(w))
                .isInstanceOf(DataIntegrityViolationException.class)
                .as("Worker email and password are NOT NULL in DB");
    }

    // ============================================================
    // ✅ TEST 4 : Save worker with roles (ManyToMany)
    // ============================================================
    @Test
    @DisplayName("Should save a worker with assigned roles")
    @Commit
    void shouldSaveWorkerWithRoles() {
        Role role = new Role();
        role.setNom("ROLE_WORKER");
        roleRepository.save(role);

        Worker w = new Worker();
        w.setEmail("alexjerdi@brain2build.com");
        w.setPassword("pass");
        w.setNom("Role");
        w.setPrenom("Tester");
        w.setDomaine("Engineering");
        w.setSpecialite("QA");
        w.setRoles(Set.of(role));

        Worker saved = workerRepository.save(w);
        assertThat(saved.getId()).isNotNull();

        Optional<Worker> found = workerRepository.findById(saved.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getRoles())
                .extracting(Role::getNom)
                .containsExactly("ROLE_WORKER");
    }
}
