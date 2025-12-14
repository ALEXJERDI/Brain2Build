package org.example.ideaservice.controller;

import org.example.ideaservice.dto.idea.IdeaCreateDto;
import org.example.ideaservice.dto.idea.IdeaReadDto;
import org.example.ideaservice.dto.idea.IdeaUpdateDto;
import org.example.ideaservice.service.IdeaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ideas")
public class IdeaController {

    private final IdeaService ideaService;

    // --------------------------------------
    // GET ALL IDEAS
    // --------------------------------------
    @GetMapping
    public ResponseEntity<List<IdeaReadDto>> getAllIdeas() {
        return ResponseEntity.ok(ideaService.getAllIdeas());
    }

    // --------------------------------------
    // GET IDEA BY ID
    // --------------------------------------
    @GetMapping("/{id}")
    public ResponseEntity<IdeaReadDto> getIdea(@PathVariable Long id) {
        return ResponseEntity.ok(ideaService.getIdea(id));
    }

    // --------------------------------------
    // CREATE IDEA
    // --------------------------------------
    @PostMapping
    public ResponseEntity<IdeaReadDto> createIdea(
            @RequestBody IdeaCreateDto dto
    ) {
        return ResponseEntity.ok(ideaService.createIdea(dto));
    }

    // --------------------------------------
    // UPDATE IDEA
    // --------------------------------------
    @PutMapping("/{id}")
    public ResponseEntity<IdeaReadDto> updateIdea(
            @PathVariable Long id,
            @RequestBody IdeaUpdateDto dto
    ) {
        return ResponseEntity.ok(ideaService.updateIdea(id, dto));
    }

    // --------------------------------------
    // APPROVE IDEA
    // --------------------------------------
    @PutMapping("/{id}/approve")
    public ResponseEntity<IdeaReadDto> approveIdea(
            @PathVariable Long id,
            @RequestParam(required = false) String feedback
    ) {
        return ResponseEntity.ok(ideaService.approveIdea(id, feedback));
    }

    // --------------------------------------
    // REJECT IDEA
    // --------------------------------------
    @PutMapping("/{id}/reject")
    public ResponseEntity<IdeaReadDto> rejectIdea(
            @PathVariable Long id,
            @RequestParam(required = false) String feedback
    ) {
        return ResponseEntity.ok(ideaService.rejectIdea(id, feedback));
    }
}
