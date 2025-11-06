package com.example.brain2build.repository;

import com.example.brain2build.domain.entity.Idea;
import com.example.brain2build.domain.entity.Ideator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestPropertySource("classpath:application-test.properties")
class IdeaRepositoryTest {

    @Autowired
    private IdeaRepository ideaRepository;

    @Autowired
    private IdeatorRepository ideatorRepository; // ✅ injection ajoutée

    @Test
    @DisplayName("save + findByTitre: should persist and retrieve an idea by its title")
    void shouldSaveAndFindByTitre() {
        Idea idea = new Idea();
        idea.setTitre("Green Energy");
        idea.setDescription("A renewable energy platform");

        ideaRepository.save(idea);

        Optional<Idea> found = ideaRepository.findByTitre("Green Energy");
        assertThat(found).isPresent();
        assertThat(found.get().getTitre()).isEqualTo("Green Energy");
    }

    @Test
    @DisplayName("findByTitreContainingIgnoreCaseOrDescriptionContainingIgnoreCase: should return ideas containing keyword")
    void shouldFindByKeywordInTitleOrDescription() {
        Idea i1 = new Idea();
        i1.setTitre("Smart Farming");
        i1.setDescription("Using AI in agriculture");

        Idea i2 = new Idea();
        i2.setTitre("AI Assistant");
        i2.setDescription("Voice recognition technology");

        ideaRepository.saveAll(List.of(i1, i2));

        List<Idea> found =
                ideaRepository.findByTitreContainingIgnoreCaseOrDescriptionContainingIgnoreCase("AI", "AI");

        assertThat(found)
                .extracting(Idea::getTitre)
                .containsExactlyInAnyOrder("Smart Farming", "AI Assistant");
    }

    @Test
    @DisplayName("findByStatus: should return all ideas with a given status")
    void shouldFindByStatus() {
        Idea i1 = new Idea();
        i1.setTitre("Idea 1");
        i1.setStatus(Idea.Status.PENDING);

        Idea i2 = new Idea();
        i2.setTitre("Idea 2");
        i2.setStatus(Idea.Status.APPROVED);

        ideaRepository.saveAll(List.of(i1, i2));

        List<Idea> found = ideaRepository.findByStatus(Idea.Status.PENDING);

        assertThat(found).hasSize(1);
        assertThat(found.get(0).getTitre()).isEqualTo("Idea 1");
    }

    @Test
    @DisplayName("findByCreatedBy: should return all ideas created by a given ideator")
    void shouldFindByCreatedBy() {
        // ✅ Crée et persiste un Ideator valide
        Ideator ideator = new Ideator();
        ideator.setNom("Alice");
        ideator.setPrenom("Smith"); // ✅ ajouté pour respecter la contrainte NOT NULL
        ideator.setEmail("alice@example.com");
        ideator.setPassword("test123"); // ✅ nécessaire (NOT NULL dans users)
        ideator = ideatorRepository.save(ideator);

        // ✅ Crée deux idées liées à cet Ideator
        Idea idea1 = new Idea();
        idea1.setTitre("Idea A");
        idea1.setCreatedBy(ideator);

        Idea idea2 = new Idea();
        idea2.setTitre("Idea B");
        idea2.setCreatedBy(ideator);

        ideaRepository.saveAll(List.of(idea1, idea2));

        // ✅ Vérifie la récupération
        List<Idea> found = ideaRepository.findByCreatedBy(ideator);
        assertThat(found).hasSize(2);
        assertThat(found)
                .extracting(Idea::getTitre)
                .containsExactlyInAnyOrder("Idea A", "Idea B");
    }
}