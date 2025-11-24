package org.example.ideaservice.Controller;

import org.example.ideaservice.Services.IdeaService;
import org.example.ideaservice.dtos.IdeaReadDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ideas")
public class IdeaController {

    private final IdeaService ideaService;

    public IdeaController(IdeaService ideaService) {
        this.ideaService = ideaService;
    }

    // Get all ideas
    @GetMapping
    public ResponseEntity<List<IdeaReadDto>> getAllIdeas() {
        return ResponseEntity.ok(ideaService.getAllIdeas());
    }

    // Get idea by ID
    @GetMapping("/{id}")
    public ResponseEntity<IdeaReadDto> getIdeaById(@PathVariable Long id) {
        return ResponseEntity.ok(ideaService.getIdeaById(id));
    }

    // Get all approved ideas
    @GetMapping("/approved")
    public ResponseEntity<List<IdeaReadDto>> getApprovedIdeas() {
        return ResponseEntity.ok(ideaService.getApprovedIdeas());
    }

    // Add a new idea
    @PostMapping
    public ResponseEntity<IdeaReadDto> addIdea(
            @RequestParam Long userId,   // User ID from request parameters
            @RequestParam String title,  // Title of the idea
            @RequestParam String description // Description of the idea
    ) {
        IdeaReadDto newIdea = ideaService.addIdea(userId, title, description); // Call service to add new idea
        return ResponseEntity.ok(newIdea);  // Return the added idea as response
    }
}
