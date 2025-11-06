package com.example.brain2build.controller;

import com.example.brain2build.domain.dto.Idea.IdeaCreateUpdateDto;
import com.example.brain2build.domain.dto.Idea.IdeaReadDto;
import com.example.brain2build.domain.dto.Idea.IdeaUpdateDto;
import com.example.brain2build.service.ideator.IdeatorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ideators")
public class IdeatorController {

    private final IdeatorService ideatorService;

    /**
     * POST /api/ideators/{ideatorId}/ideas
     * -> l’ideator propose une nouvelle idée
     */
    @PostMapping("/{ideatorId}/CreateIdeas")
    @PreAuthorize("#ideatorId == authentication.principal.id or hasRole('ADMIN')")
    public ResponseEntity<IdeaReadDto> proposeIdea(
            @PathVariable Long ideatorId,
            @Valid @RequestBody IdeaCreateUpdateDto dto
    ) {
        IdeaReadDto created = ideatorService.proposeIdea(ideatorId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * GET /api/ideators/{ideatorId}/ideas
     * -> récupérer toutes les idées d’un ideator
     */
    @GetMapping("/{ideatorId}/ideas")
    @PreAuthorize("#ideatorId == authentication.principal.id or hasRole('ADMIN')")
    public List<IdeaReadDto> getMyIdeas(@PathVariable Long ideatorId) {
        return ideatorService.getMyIdeas(ideatorId);
    }

    /**
     * PUT /api/ideators/{ideatorId}/ideas/{ideaId}
     * -> mettre à jour une idée (seulement si elle lui appartient et est PENDING)
     */
    @PutMapping("/{ideatorId}/UpdateIdeas/{ideaId}")
    @PreAuthorize("#ideatorId == authentication.principal.id or hasRole('ADMIN')")
    public IdeaReadDto updateMyIdea(
            @PathVariable Long ideatorId,
            @PathVariable Long ideaId,
            @Valid @RequestBody IdeaUpdateDto dto
    ) {
        return ideatorService.updateMyIdea(ideatorId, ideaId, dto);
    }

    /**
     * DELETE /api/ideators/{ideatorId}/ideas/{ideaId}
     * -> supprimer une idée (seulement si elle lui appartient et est PENDING)
     */
    @DeleteMapping("/{ideatorId}/DeleteIdeas/{ideaId}")
    @PreAuthorize("#ideatorId == authentication.principal.id or hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMyIdea(
            @PathVariable Long ideatorId,
            @PathVariable Long ideaId
    ) {
        ideatorService.deleteMyIdea(ideatorId, ideaId);
    }

    /**
     * GET /api/ideators/{ideatorId}/ideas/{ideaId}/feedback
     * -> récupérer le feedback sur une idée
     */
    @GetMapping("/{ideatorId}/ideas/{ideaId}/feedback")
    @PreAuthorize("#ideatorId == authentication.principal.id or hasRole('ADMIN')")
    public ResponseEntity<String> getIdeaFeedback(
            @PathVariable Long ideatorId,
            @PathVariable Long ideaId
    ) {
        String feedback = ideatorService.getIdeaFeedback(ideatorId, ideaId);
        return ResponseEntity.ok(feedback);
    }
}