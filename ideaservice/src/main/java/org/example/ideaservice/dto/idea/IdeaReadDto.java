package org.example.ideaservice.dto.idea;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class IdeaReadDto {
    private Long id;
    private String titre;
    private String description;
    private String status;
    private LocalDateTime createdAt;
    private String feedback;
    private Long creatorId;
}

