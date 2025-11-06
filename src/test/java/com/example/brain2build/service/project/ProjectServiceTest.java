package com.example.brain2build.service.project;

import com.example.brain2build.domain.dto.Project.ProjectUpdateDto;
import com.example.brain2build.domain.entity.Project;
import com.example.brain2build.repository.ProjectRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private ProjectServiceImpl projectService;

    @Test
    @DisplayName("Should update existing project")
    void shouldUpdateProject() {
        // given
        Project existing = new Project();
        existing.setId(1L);
        existing.setProjectName("Old Name");

        when(projectRepository.findById(1L)).thenReturn(Optional.of(existing));

        ProjectUpdateDto dto = new ProjectUpdateDto("New Brain Project", null);

        // when
        projectService.updateProject(1L, dto);

        // then
        assertThat(existing.getProjectName()).isEqualTo("New Brain Project");
        verify(projectRepository).save(existing);
    }
}