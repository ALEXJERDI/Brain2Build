package org.example.projectservice.repository;

import org.example.projectservice.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    // 🔍 Trouver un projet par son nom
    Optional<Project> findByProjectName(String projectName);

    // 🔍 Filtrer les projets selon leur statut
    List<Project> findByStatus(Project.Status status);

    // 🔍 Rechercher des projets contenant un mot-clé
    List<Project> findByProjectNameContainingIgnoreCase(String keyword);
}
