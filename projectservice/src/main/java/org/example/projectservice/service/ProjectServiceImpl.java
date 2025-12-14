package org.example.projectservice.service;

import lombok.RequiredArgsConstructor;
import org.example.projectservice.config.RabbitMQConfig;
import org.example.projectservice.dto.Project.ProjectCreateUpdateDto;
import org.example.projectservice.dto.Project.ProjectReadDto;
import org.example.projectservice.dto.Project.ProjectUpdateDto;
import org.example.projectservice.entity.Project;
import org.example.projectservice.mapper.ProjectMapper;
import org.example.projectservice.repository.ProjectRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;

    /**
     * ⚠️ IMPORTANT :
     * Cette méthode ne valide plus d'idées via Feign.
     * Elle ne publie plus de ProjectCreatedEvent vers room-service.
     * C’est maintenant le rôle du ProjectCreationService
     * (appelé automatiquement via RabbitMQ).
     */
    @Override
    public ProjectReadDto createProject(ProjectCreateUpdateDto dto) {

        Project project = projectMapper.toEntity(dto);
        project.setStatus(Project.Status.IN_PROGRESS);
        project.setStartDate(LocalDateTime.now());

        Project saved = projectRepository.save(project);

        return projectMapper.toReadDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public ProjectReadDto getProjectById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + id));
        return projectMapper.toReadDto(project);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectReadDto> getAllProjects() {
        return projectMapper.toReadDtoList(projectRepository.findAll());
    }

    @Override
    public ProjectReadDto updateProject(Long id, ProjectUpdateDto dto) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + id));

        projectMapper.updateProjectFromDto(dto, project);

        Project saved = projectRepository.save(project);
        return projectMapper.toReadDto(saved);
    }

    @Override
    public ProjectReadDto updateProjectStatus(Long id, String status) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + id));

        Project.Status newStatus = Project.Status.valueOf(status.toUpperCase());
        project.setStatus(newStatus);

        if (newStatus == Project.Status.COMPLETED) {
            project.setEndDate(LocalDateTime.now());
        }

        Project saved = projectRepository.save(project);
        return projectMapper.toReadDto(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectReadDto> searchProjects(String keyword) {
        return projectMapper.toReadDtoList(
                projectRepository.findByProjectNameContainingIgnoreCase(keyword)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProjectReadDto> getProjectsByStatus(String status) {
        Project.Status enumStatus = Project.Status.valueOf(status.toUpperCase());
        return projectMapper.toReadDtoList(
                projectRepository.findByStatus(enumStatus)
        );
    }

    @Override
    public void deleteProject(Long id) {
        if (!projectRepository.existsById(id)) {
            throw new RuntimeException("Project not found with id: " + id);
        }
        projectRepository.deleteById(id);
    }
}
