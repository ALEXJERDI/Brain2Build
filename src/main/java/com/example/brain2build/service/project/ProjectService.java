package com.example.brain2build.service.project;

import com.example.brain2build.domain.dto.Project.ProjectCreateUpdateDto;
import com.example.brain2build.domain.dto.Project.ProjectReadDto;

import java.util.List;

public interface ProjectService {

    /**
     * Créer un nouveau projet et son Room associé.
     */
    ProjectReadDto createProject(ProjectCreateUpdateDto dto);

    /**
     * Obtenir un projet par son ID.
     */
    ProjectReadDto getProjectById(Long id);

    /**
     * Récupérer la liste de tous les projets.
     */
    List<ProjectReadDto> getAllProjects();

    /**
     * Mettre à jour un projet (nom, idées liées...).
     */
    ProjectReadDto updateProject(Long id, ProjectCreateUpdateDto dto);

    /**
     * Mettre à jour le statut d’un projet (IN_PROGRESS, COMPLETED, ARCHIVED).
     */
    ProjectReadDto updateProjectStatus(Long id, String status);

    /**
     * Rechercher des projets par mot-clé (nom partiel).
     */
    List<ProjectReadDto> searchProjects(String keyword);

    /**
     * Obtenir les projets selon leur statut.
     */
    List<ProjectReadDto> getProjectsByStatus(String status);

    /**
     * Supprimer un projet (et le room associé).
     */
    void deleteProject(Long id);
}
