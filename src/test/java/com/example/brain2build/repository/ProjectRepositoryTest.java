package com.example.brain2build.repository;

import com.example.brain2build.domain.entity.Project;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test") // ⚙️ utilise application-test.yml (H2)
class ProjectRepositoryTest {

    @Autowired
    private ProjectRepository projectRepository;

    @Test
    @DisplayName("Should save and find a project by name")
    void shouldSaveAndFindProject() {
        Project project = new Project();
        project.setProjectName("AI Assistant");
        projectRepository.save(project);

        List<Project> found = projectRepository.findByProjectNameContainingIgnoreCase("ai");
        assertThat(found).hasSize(1);
        assertThat(found.get(0).getProjectName()).isEqualTo("AI Assistant");
    }

    @Test
    @DisplayName("Should delete project by ID")
    void shouldDeleteProjectById() {
        Project project = new Project();
        project.setProjectName("To Delete");
        projectRepository.save(project);

        Long id = project.getId();
        projectRepository.deleteById(id);

        Optional<Project> deleted = projectRepository.findById(id);
        assertThat(deleted).isEmpty();
    }
}