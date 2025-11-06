package com.example.brain2build.controller;

import com.example.brain2build.domain.dto.Idea.*;
import com.example.brain2build.service.idea.IdeaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for managing Ideas
 */
@RestController
@RequestMapping("/api/ideas")
@RequiredArgsConstructor
public class IdeaController {

    private final IdeaService ideaService;

    // 🧩 Récupérer toutes les idées
    @GetMapping
    public ResponseEntity<List<IdeaReadDto>> getAllIdeas() {
        return ResponseEntity.ok(ideaService.getAllIdeas());
    }

    // 🔍 Rechercher une idée par ID
    @GetMapping("/{id}")
    public ResponseEntity<IdeaReadDto> getIdeaById(@PathVariable Long id) {
        return ResponseEntity.ok(ideaService.getIdeaById(id));
    }

    // 🔍 Recherche par mot-clé (titre ou description)
    @GetMapping("/search")
    public ResponseEntity<List<IdeaReadDto>> searchIdeas(@RequestParam String keyword) {
        return ResponseEntity.ok(ideaService.searchIdeas(keyword));
    }

    // ✅ Récupérer toutes les idées approuvées (pour affichage public par ex)
    @GetMapping("/approved")
    public ResponseEntity<List<IdeaSimpleDto>> getApprovedIdeas() {
        return ResponseEntity.ok(ideaService.getApprovedIdeas());
    }

    // 🟢 Approuver une idée avec feedback
    @PutMapping("/{id}/approve")
    public ResponseEntity<IdeaReadDto> approveIdea(
            @PathVariable Long id,
            @RequestParam(required = false) String feedback
    ) {
        return ResponseEntity.ok(ideaService.approveIdea(id, feedback));
    }

    // 🔴 Rejeter une idée avec feedback
    @PutMapping("/{id}/reject")
    public ResponseEntity<IdeaReadDto> rejectIdea(
            @PathVariable Long id,
            @RequestParam(required = false) String feedback
    ) {
        return ResponseEntity.ok(ideaService.rejectIdea(id, feedback));
    }


}