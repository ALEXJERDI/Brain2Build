package com.example.brain2build.repository;

import com.example.brain2build.domain.entity.Idea;
import com.example.brain2build.domain.entity.Ideator;
import com.example.brain2build.domain.entity.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
@TestPropertySource("classpath:application-test.properties")
class IdeatorRepositoryTest {

    @Autowired
    private IdeatorRepository ideatorRepository;

    @Autowired
    private IdeaRepository ideaRepository;

    @Autowired
    private RoleRepository roleRepository;

    @BeforeEach
    void cleanDatabase() {
        ideaRepository.deleteAll();
        ideatorRepository.deleteAll();
        roleRepository.deleteAll();
    }

    // ============================================================
    // ✅ TEST 1 : Save + Find by Email
    // ============================================================
    @Test
    @DisplayName("Should save and find Ideator by email")
    void shouldSaveAndFindIdeatorByEmail() {
        Ideator ideator = new Ideator();
        ideator.setEmail("stevejobs@brain2build.com");
        ideator.setPassword("apple123");
        ideator.setNom("Jobs");
        ideator.setPrenom("Steve");
        ideator.setTelephone("0600000000");
        ideator.setBio("Innovator & Visionary");
        ideator.setIdeaCount(5);

        Ideator saved = ideatorRepository.save(ideator);
        assertThat(saved.getId()).isNotNull();

        Optional<Ideator> found = ideatorRepository.findByEmail("stevejobs@brain2build.com");
        assertThat(found).isPresent();
        assertThat(found.get().getBio()).contains("Innovator");
        assertThat(found.get().getIdeaCount()).isEqualTo(5);
    }

    // ============================================================
    // ✅ TEST 2 : Constraint NOT NULL
    // ============================================================
    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    @DisplayName("Should reject Ideator with null email or password")
    void shouldRejectNullEmailOrPassword() {
        Ideator ideator = new Ideator();
        ideator.setNom("NoEmail");
        ideator.setPrenom("User");
        ideator.setBio("Missing credentials");

        assertThatThrownBy(() -> ideatorRepository.saveAndFlush(ideator))
                .isInstanceOf(DataIntegrityViolationException.class)
                .as("Ideator email and password must be NOT NULL");
    }

    // ============================================================
    // ✅ TEST 3 : Relation Ideator → Ideas (OneToMany)
    // ============================================================
    @Test
    @Commit
    @DisplayName("Should save Ideator with associated Ideas")
    void shouldSaveIdeatorWithIdeas() {
        // 1️⃣ Create Ideator
        Ideator ideator = new Ideator();
        ideator.setEmail("billgates@brain2build.com");
        ideator.setPassword("microsoft");
        ideator.setNom("Gates");
        ideator.setPrenom("Bill");
        ideator.setBio("Tech entrepreneur");
        ideator.setIdeaCount(2);

        // 2️⃣ Create two ideas and link both sides manually
        Idea idea1 = new Idea();
        idea1.setTitre("Smart Agriculture");
        idea1.setDescription("IoT sensors for crops");
        idea1.setStatus(Idea.Status.APPROVED);
        idea1.setCreatedAt(LocalDateTime.now());
        idea1.setCreatedBy(ideator); // child knows parent

        Idea idea2 = new Idea();
        idea2.setTitre("AI Tutor");
        idea2.setDescription("Personal learning assistant");
        idea2.setStatus(Idea.Status.PENDING);
        idea2.setCreatedAt(LocalDateTime.now());
        idea2.setCreatedBy(ideator); // child knows parent

        // ✅ Important: parent also knows children
        ideator.getIdeas().addAll(List.of(idea1, idea2));

        // 3️⃣ Save Ideator (cascade will save ideas too)
        ideatorRepository.saveAndFlush(ideator);

        // 4️⃣ Verify
        Optional<Ideator> found = ideatorRepository.findByEmail("billgates@brain2build.com");
        assertThat(found).isPresent();
        assertThat(found.get().getIdeas()).hasSize(2);
        assertThat(found.get().getIdeas())
                .extracting(Idea::getTitre)
                .containsExactlyInAnyOrder("Smart Agriculture", "AI Tutor");
    }

    // ============================================================
    // ✅ TEST 4 : Save Ideator with Role
    // ============================================================
    @Test
    @Commit
    @DisplayName("Should save Ideator with assigned Role")
    void shouldSaveIdeatorWithRole() {
        // 1️⃣ Create role
        Role role = new Role();
        role.setNom("ROLE_IDEATOR");
        roleRepository.save(role);

        // 2️⃣ Create ideator with this role
        Ideator ideator = new Ideator();
        ideator.setEmail("elonmusk@brain2build.com");
        ideator.setPassword("spacex123");
        ideator.setNom("Musk");
        ideator.setPrenom("Elon");
        ideator.setBio("Inventor of rockets 🚀");
        ideator.setIdeaCount(10);
        ideator.setRoles(Set.of(role));

        Ideator saved = ideatorRepository.saveAndFlush(ideator);

        // 3️⃣ Verify role link
        Optional<Ideator> found = ideatorRepository.findById(saved.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getRoles())
                .extracting(Role::getNom)
                .containsExactly("ROLE_IDEATOR");
    }
}
