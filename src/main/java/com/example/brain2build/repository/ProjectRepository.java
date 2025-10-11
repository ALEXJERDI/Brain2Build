package com.example.brain2build.repository;

import com.example.brain2build.domain.entity.Project;
import com.example.brain2build.domain.entity.Idea;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    // 🔍 Trouver un projet par son nom
    Optional<Project> findByProjectName(String projectName);

    // 🔍 Tous les projets liés à une idée donnée
    List<Project> findByIdea(Idea idea);

    // 🔍 Filtrer les projets selon leur statut
    List<Project> findByStatus(Project.Status status);

    // 🔍 Rechercher des projets contenant un mot-clé
    List<Project> findByProjectNameContainingIgnoreCase(String keyword);
}
