package org.example.ideaservice.dto.idea;

import lombok.Data;

@Data
public class IdeaCreateDto {
    private String titre;
    private String description;
    private Long creatorId; // coming from user-service
}
