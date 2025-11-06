package com.example.brain2build.controller;

import com.example.brain2build.domain.dto.Worker.*;
import com.example.brain2build.service.worker.WorkerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workers")
@RequiredArgsConstructor
@Validated
public class WorkerController {

    private final WorkerService workerService;

    // 🔹 READ (single)
    @GetMapping("/{id}")
    public ResponseEntity<WorkerReadDto> getWorkerById(@PathVariable Long id) {
        return ResponseEntity.ok(workerService.getWorkerById(id));
    }

    // 🔹 READ (all)
    @GetMapping
    public ResponseEntity<List<WorkerReadDto>> getAllWorkers() {
        return ResponseEntity.ok(workerService.getAllWorkers());
    }

    // 🔹 UPDATE
    @PutMapping("/{id}")
    @PreAuthorize("#id == authentication.principal.id or hasRole('ADMIN')")
    public ResponseEntity<WorkerReadDto> updateWorker(
            @PathVariable Long id,
            @RequestBody @Validated WorkerCreateUpdateDto dto
    ) {
        return ResponseEntity.ok(workerService.updateWorker(id, dto));
    }

    // 🔹 DELETE
    @DeleteMapping("/{id}")
    @PreAuthorize("#id == authentication.principal.id or hasRole('ADMIN')")
    public ResponseEntity<Void> deleteWorker(@PathVariable Long id) {
        workerService.deleteWorker(id);
        return ResponseEntity.noContent().build();
    }
    // 🔹 SEARCH
    @GetMapping("/search")
    public ResponseEntity<List<WorkerReadDto>> searchWorkers(
            @RequestParam(required = false) String domaine,
            @RequestParam(required = false) String specialite
    ) {
        if (domaine != null && !domaine.isBlank()) {
            return ResponseEntity.ok(workerService.searchByDomaine(domaine));
        } else if (specialite != null && !specialite.isBlank()) {
            return ResponseEntity.ok(workerService.searchBySpecialite(specialite));
        }
        return ResponseEntity.badRequest().build();
    }

}