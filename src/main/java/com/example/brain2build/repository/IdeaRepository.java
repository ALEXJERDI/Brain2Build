package com.example.brain2build.repository;

import com.example.brain2build.domain.entity.Idea;
import com.example.brain2build.domain.entity.Ideator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IdeaRepository extends JpaRepository<Idea, Long> {

    // 🔍 Trouver une idée par son titre (exact)
    Optional<Idea> findByTitre(String title);

    // 🔍 Rechercher des idées contenant un mot-clé dans le titre ou la description
    List<Idea> findByTitreContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String titre, String description);

    // 🔍 Trouver toutes les idées d’un ideator
    List<Idea> findByCreatedBy(Ideator ideator);

    // 🔍 Filtrer par statut
    List<Idea> findByStatus(Idea.Status status);
}

