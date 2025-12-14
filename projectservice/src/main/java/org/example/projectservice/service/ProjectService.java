package org.example.projectservice.service;

import org.example.projectservice.dto.Project.ProjectCreateUpdateDto;
import org.example.projectservice.dto.Project.ProjectReadDto;
import org.example.projectservice.dto.Project.ProjectUpdateDto;

import java.util.List;

public interface ProjectService {

    /**
     * Créer un nouveau projet.
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
    ProjectReadDto updateProject(Long id, ProjectUpdateDto dto);

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
     * Supprimer un projet.
     */
    void deleteProject(Long id);
}
