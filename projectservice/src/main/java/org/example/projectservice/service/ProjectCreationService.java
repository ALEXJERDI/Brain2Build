package org.example.projectservice.service;

import lombok.RequiredArgsConstructor;
import org.example.projectservice.entity.Project;
import org.example.contracts.events.IdeaApprovedEvent;
import org.example.contracts.events.ProjectCreatedEvent;
import org.example.contracts.events.RequestActivatedEvent;
import org.example.projectservice.messaging.EventPublisher;
import org.example.projectservice.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Set;

import static org.example.projectservice.entity.Project.Status.IN_PROGRESS;

@Service
@RequiredArgsConstructor
public class ProjectCreationService {


    private final ProjectRepository projectRepository;
    private final EventPublisher eventPublisher;

    // -----------------------------------------
    // 🚀 PROJECT FROM IDEA
    // -----------------------------------------
    public void createProjectFromIdea(IdeaApprovedEvent event) {

        Project project = new Project();
        project.setProjectName(event.getTitre());
        project.setDescription(event.getDescription());
        project.setCreatorId(event.getCreatorId());
        project.setStatus(IN_PROGRESS);
        project.setStartDate(LocalDateTime.now());

        // Idea-related
        project.setIdeaIds(Set.of(event.getIdeaId()));
        project.setSourceType("IDEA");
        project.setSourceId(event.getIdeaId());

        Project saved = projectRepository.save(project);

        // Send to Room-Service
        ProjectCreatedEvent roomEvent = ProjectCreatedEvent.builder()
                .projectId(saved.getId())
                .projectName(saved.getProjectName())
                .description(saved.getDescription())
                .creatorId(saved.getCreatorId())
                .build();

        eventPublisher.publishProjectCreated(roomEvent);
    }


    // -----------------------------------------
    // 🚀 PROJECT FROM REQUEST
    // -----------------------------------------
    public void createProjectFromRequest(RequestActivatedEvent event) {

        Project project = new Project();
        project.setProjectName(event.getTitre());
        project.setDescription(event.getDescription());
        project.setCreatorId(event.getCreatorId());
        project.setStatus(IN_PROGRESS);
        project.setStartDate(LocalDateTime.now());

        // Request-related
        project.setSourceType("REQUEST");
        project.setSourceId(event.getRequestId());

        // No idea IDs when project originates from request
        project.setIdeaIds(Collections.emptySet());

        Project saved = projectRepository.save(project);

        // Send to Room-Service
        ProjectCreatedEvent roomEvent = ProjectCreatedEvent.builder()
                .projectId(saved.getId())
                .projectName(saved.getProjectName())
                .description(saved.getDescription())
                .creatorId(saved.getCreatorId())
                .build();

        eventPublisher.publishProjectCreated(roomEvent);
    }
}
