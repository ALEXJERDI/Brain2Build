package org.example.projectservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.projectservice.dto.Project.ProjectCreateUpdateDto;
import org.example.projectservice.dto.Project.ProjectReadDto;
import org.example.projectservice.dto.Project.ProjectUpdateDto;
import org.example.projectservice.service.ProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
@Validated
public class ProjectController {

    private final ProjectService projectService;

    // 🔹 CREATE – Créer un projet
    @PostMapping
    public ResponseEntity<ProjectReadDto> createProject(@RequestBody @Valid ProjectCreateUpdateDto dto) {
        return ResponseEntity.ok(projectService.createProject(dto));
    }

    // 🔹 READ – Récupérer un projet par ID
    @GetMapping("/{id}")
    public ResponseEntity<ProjectReadDto> getProjectById(@PathVariable Long id) {
        return ResponseEntity.ok(projectService.getProjectById(id));
    }

    // 🔹 READ – Lister tous les projets
    @GetMapping
    public ResponseEntity<List<ProjectReadDto>> getAllProjects() {
        return ResponseEntity.ok(projectService.getAllProjects());
    }

    // 🔹 UPDATE – Modifier les infos d’un projet (nom, idées, etc.)
    @PutMapping("/{id}")
    public ResponseEntity<ProjectReadDto> updateProject(
            @PathVariable Long id,
            @RequestBody @Validated ProjectUpdateDto dto
    ) {
        return ResponseEntity.ok(projectService.updateProject(id, dto));
    }

    // 🔹 UPDATE STATUS – Modifier uniquement le statut
    @PatchMapping("/{id}/status")
    public ResponseEntity<ProjectReadDto> updateProjectStatus(
            @PathVariable Long id,
            @RequestParam String status
    ) {
        return ResponseEntity.ok(projectService.updateProjectStatus(id, status));
    }

    // 🔹 SEARCH – Rechercher par mot-clé (nom partiel)
    @GetMapping("/search")
    public ResponseEntity<List<ProjectReadDto>> searchProjects(@RequestParam String keyword) {
        return ResponseEntity.ok(projectService.searchProjects(keyword));
    }

    // 🔹 FILTER – Récupérer les projets par statut
    @GetMapping("/status/{status}")
    public ResponseEntity<List<ProjectReadDto>> getProjectsByStatus(@PathVariable String status) {
        return ResponseEntity.ok(projectService.getProjectsByStatus(status));
    }

    // 🔹 DELETE – Supprimer un projet
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }
}
