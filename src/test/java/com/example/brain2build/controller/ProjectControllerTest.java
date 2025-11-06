package com.example.brain2build.controller;

import com.example.brain2build.domain.dto.Project.*;
import com.example.brain2build.service.project.ProjectService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProjectController.class)
class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProjectService projectService;

    @Autowired
    private ObjectMapper objectMapper;

    private ProjectReadDto projectReadDto;

    @BeforeEach
    void setUp() {
        projectReadDto = new ProjectReadDto(
                1L,
                "Brain2Build Platform",
                com.example.brain2build.domain.entity.Project.Status.IN_PROGRESS,
                LocalDateTime.now(),
                null,
                Set.of(),
                null
        );
    }

    @Test
    @DisplayName("✅ createProject() doit créer un projet et retourner 200 OK")
    void createProject_ShouldReturnOk() throws Exception {
        ProjectCreateUpdateDto createDto = new ProjectCreateUpdateDto("Brain2Build Platform", Set.of(1L, 2L));

        Mockito.when(projectService.createProject(any(ProjectCreateUpdateDto.class)))
                .thenReturn(projectReadDto);

        mockMvc.perform(post("/api/projects")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.projectName").value("Brain2Build Platform"));
    }

    @Test
    @DisplayName("✅ getAllProjects() doit retourner la liste des projets")
    void getAllProjects_ShouldReturnList() throws Exception {
        Mockito.when(projectService.getAllProjects())
                .thenReturn(List.of(projectReadDto));

        mockMvc.perform(get("/api/projects"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].projectName").value("Brain2Build Platform"));
    }

    @Test
    @DisplayName("✅ getProjectById() doit retourner un projet existant")
    void getProjectById_ShouldReturnProject() throws Exception {
        Mockito.when(projectService.getProjectById(1L))
                .thenReturn(projectReadDto);

        mockMvc.perform(get("/api/projects/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.projectName").value("Brain2Build Platform"));
    }

    @Test
    @DisplayName("✅ updateProject() doit mettre à jour un projet")
    void updateProject_ShouldReturnUpdatedProject() throws Exception {
        ProjectCreateUpdateDto updateDto = new ProjectCreateUpdateDto("Updated Project", Set.of(3L));

        ProjectReadDto updatedDto = new ProjectReadDto(
                1L,
                "Updated Project",
                com.example.brain2build.domain.entity.Project.Status.IN_PROGRESS,
                LocalDateTime.now(),
                null,
                Set.of(),
                null
        );

        Mockito.when(projectService.updateProject(eq(1L), any(ProjectUpdateDto.class)))
                .thenReturn(updatedDto);

        mockMvc.perform(put("/api/projects/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.projectName").value("Updated Project"));
    }

    @Test
    @DisplayName("✅ deleteProject() doit supprimer un projet et retourner 204")
    void deleteProject_ShouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/api/projects/1"))
                .andExpect(status().isNoContent());
        Mockito.verify(projectService, Mockito.times(1)).deleteProject(1L);
    }

    @Test
    @DisplayName("✅ searchProjects() doit retourner les projets filtrés")
    void searchProjects_ShouldReturnResults() throws Exception {
        Mockito.when(projectService.searchProjects("Brain"))
                .thenReturn(List.of(projectReadDto));

        mockMvc.perform(get("/api/projects/search?keyword=Brain"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].projectName").value("Brain2Build Platform"));
    }

    @Test
    @DisplayName("✅ updateProjectStatus() doit changer le statut")
    void updateProjectStatus_ShouldReturnUpdatedStatus() throws Exception {
        ProjectReadDto completedProject = new ProjectReadDto(
                1L,
                "Brain2Build Platform",
                com.example.brain2build.domain.entity.Project.Status.COMPLETED,
                LocalDateTime.now(),
                LocalDateTime.now(),
                Set.of(),
                null
        );

        Mockito.when(projectService.updateProjectStatus(1L, "COMPLETED"))
                .thenReturn(completedProject);

        mockMvc.perform(patch("/api/projects/1/status")
                        .param("status", "COMPLETED"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("COMPLETED"));
    }
}