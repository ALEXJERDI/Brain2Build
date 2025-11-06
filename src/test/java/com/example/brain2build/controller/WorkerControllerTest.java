package com.example.brain2build.controller;

import com.example.brain2build.controller.WorkerController;
import com.example.brain2build.domain.dto.Worker.*;
import com.example.brain2build.service.worker.WorkerService;
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

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(WorkerController.class)
class WorkerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WorkerService workerService;

    @Autowired
    private ObjectMapper objectMapper;

    private WorkerReadDto workerReadDto;

    @BeforeEach
    void setUp() {
        workerReadDto = new WorkerReadDto(
                1L,
                "worker@example.com",
                "Doe",
                "John",
                "+33655555555",
                "Backend",
                "Spring Boot",
                3,
                "https://portfolio.io/john"
        );
    }



    // 🔹 READ single
    @Test
    @DisplayName("✅ getWorkerById() doit retourner un Worker existant")
    void getWorkerById_ShouldReturnWorker() throws Exception {
        Mockito.when(workerService.getWorkerById(1L))
                .thenReturn(workerReadDto);

        mockMvc.perform(get("/api/workers/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.email").value("worker@example.com"));
    }

    // 🔹 READ all
    @Test
    @DisplayName("✅ getAllWorkers() doit retourner la liste des Workers")
    void getAllWorkers_ShouldReturnList() throws Exception {
        Mockito.when(workerService.getAllWorkers())
                .thenReturn(List.of(workerReadDto));

        mockMvc.perform(get("/api/workers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].specialite").value("Spring Boot"));
    }

    // 🔹 UPDATE
    @Test
    @DisplayName("✅ updateWorker() doit mettre à jour un Worker existant")
    void updateWorker_ShouldReturnUpdatedWorker() throws Exception {
        WorkerCreateUpdateDto updateDto = new WorkerCreateUpdateDto(
                "worker@example.com",
                "NewPassword123",
                "Doe",
                "John",
                "+33666666666",
                "Backend",
                "Java",
                4,
                "https://portfolio.io/john"
        );

        WorkerReadDto updatedWorker = new WorkerReadDto(
                1L,
                "worker@example.com",
                "Doe",
                "John",
                "+33666666666",
                "Backend",
                "Java",
                4,
                "https://portfolio.io/john"
        );

        Mockito.when(workerService.updateWorker(eq(1L), any(WorkerUpdateDto.class)))
                .thenReturn(updatedWorker);

        mockMvc.perform(put("/api/workers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.specialite").value("Java"))
                .andExpect(jsonPath("$.experience").value(4));
    }

    // 🔹 DELETE
    @Test
    @DisplayName("✅ deleteWorker() doit supprimer un Worker et retourner 204 No Content")
    void deleteWorker_ShouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/api/workers/1"))
                .andExpect(status().isNoContent());
        Mockito.verify(workerService, Mockito.times(1)).deleteWorker(1L);
    }

    // 🔹 SEARCH domaine
    @Test
    @DisplayName("✅ searchWorkers() par domaine doit retourner des résultats")
    void searchWorkers_ByDomaine_ShouldReturnList() throws Exception {
        Mockito.when(workerService.searchByDomaine("Backend"))
                .thenReturn(List.of(workerReadDto));

        mockMvc.perform(get("/api/workers/search?domaine=Backend"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].domaine").value("Backend"));
    }

    // 🔹 SEARCH specialite
    @Test
    @DisplayName("✅ searchWorkers() par spécialité doit retourner des résultats")
    void searchWorkers_BySpecialite_ShouldReturnList() throws Exception {
        Mockito.when(workerService.searchBySpecialite("Spring"))
                .thenReturn(List.of(workerReadDto));

        mockMvc.perform(get("/api/workers/search?specialite=Spring"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].specialite").value("Spring Boot"));
    }

    // 🔹 SEARCH erreur (aucun paramètre)
    @Test
    @DisplayName("⚠️ searchWorkers() sans paramètre doit retourner 400 Bad Request")
    void searchWorkers_WithoutParams_ShouldReturnBadRequest() throws Exception {
        mockMvc.perform(get("/api/workers/search"))
                .andExpect(status().isBadRequest());
    }
}