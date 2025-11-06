package com.example.brain2build.service.project;

import com.example.brain2build.domain.dto.Project.*;
import com.example.brain2build.domain.entity.Idea;
import com.example.brain2build.domain.entity.Project;
import com.example.brain2build.domain.entity.Room;
import com.example.brain2build.mappers.ProjectMapper;
import com.example.brain2build.repository.IdeaRepository;
import com.example.brain2build.repository.ProjectRepository;
import com.example.brain2build.repository.RoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final IdeaRepository ideaRepository;
    private final RoomRepository roomRepository;
    private final ProjectMapper projectMapper;

    /**
     * 🟢 Créer un projet à partir d'un DTO et créer automatiquement son Room associé.
     */
    @Override
    public ProjectReadDto createProject(ProjectCreateUpdateDto dto) {
        Project project = projectMapper.toEntity(dto);

        // 🔗 Lier les idées si elles existent
        if (dto.getIdeaIds() != null && !dto.getIdeaIds().isEmpty()) {
            Set<Idea> ideas = dto.getIdeaIds().stream()
                    .map(id -> ideaRepository.findById(id)
                            .orElseThrow(() -> new RuntimeException("Idea not found with id: " + id)))
                    .collect(Collectors.toSet());

            // Validation : les idées doivent être ACCEPTED
            ideas.forEach(idea -> {
                if (!idea.getStatus().equals(Idea.Status.APPROVED)) {
                    throw new RuntimeException("Idea " + idea.getId() + " is not accepted yet!");
                }
            });

            project.setIdeas(ideas);
        }

        project.setStatus(Project.Status.IN_PROGRESS);
        project.setStartDate(LocalDateTime.now());

        project = projectRepository.save(project);

        // 🧱 Créer automatiquement le Room du projet
        Room room = new Room();
        room.setRoomName("Room_" + project.getProjectName());
        room.setProject(project);
        roomRepository.save(room);

        project.setRoom(room);

        return projectMapper.toReadDto(project);
    }

    /**
     * 🔍 Obtenir un projet par ID.
     */
    @Override
    public ProjectReadDto getProjectById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        return projectMapper.toReadDto(project);
    }

    /**
     * 📋 Obtenir la liste de tous les projets.
     */
    @Override
    public List<ProjectReadDto> getAllProjects() {
        return projectRepository.findAll()
                .stream()
                .map(projectMapper::toReadDto)
                .collect(Collectors.toList());
    }

    /**
     * 🛠️ Mettre à jour un projet existant.
     */
    @Override
    @Transactional
    public ProjectReadDto updateProject(Long id, ProjectUpdateDto dto) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        // ✅ Only update name if it is provided
        if (dto.getProjectName() != null && !dto.getProjectName().isBlank()) {
            project.setProjectName(dto.getProjectName());
        }

        // ✅ Only update ideas if ideaIds is provided
        if (dto.getIdeaIds() != null) {
            Set<Idea> ideas = dto.getIdeaIds().stream()
                    .map(ideaId -> ideaRepository.findById(ideaId)
                            .orElseThrow(() -> new RuntimeException("Idea not found with id: " + ideaId)))
                    .collect(Collectors.toSet());
            project.setIdeas(ideas);
        }

        Project saved = projectRepository.save(project);
        return projectMapper.toReadDto(saved);
    }


    /**
     * 🔁 Mettre à jour le statut du projet (et le Room associé).
     */
    @Override
    public ProjectReadDto updateProjectStatus(Long id, String status) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        Project.Status newStatus = Project.Status.valueOf(status.toUpperCase());
        project.setStatus(newStatus);

        if (newStatus == Project.Status.COMPLETED) {
            project.setEndDate(LocalDateTime.now());

            // 🔒 Verrouiller le Room associé
            roomRepository.findByProject_Id(id).ifPresent(room -> {
                room.setFull(true);
                roomRepository.save(room);
            });
        }

        projectRepository.save(project);
        return projectMapper.toReadDto(project);
    }

    /**
     * 🔎 Rechercher des projets par nom.
     */
    @Override
    public List<ProjectReadDto> searchProjects(String keyword) {
        return projectRepository.findByProjectNameContainingIgnoreCase(keyword)
                .stream()
                .map(projectMapper::toReadDto)
                .collect(Collectors.toList());
    }

    /**
     * 📊 Filtrer les projets par statut.
     */
    @Override
    public List<ProjectReadDto> getProjectsByStatus(String status) {
        Project.Status enumStatus = Project.Status.valueOf(status.toUpperCase());
        return projectRepository.findByStatus(enumStatus)
                .stream()
                .map(projectMapper::toReadDto)
                .collect(Collectors.toList());
    }

    /**
     * 🗑️ Supprimer un projet et son Room automatiquement.
     */
    @Override
    public void deleteProject(Long id) {
        if (!projectRepository.existsById(id)) {
            throw new RuntimeException("Project not found");
        }
        projectRepository.deleteById(id);
    }
}