package org.example.ideaservice.dtos;


import lombok.AllArgsConstructor;
import lombok.Value;
import org.example.ideaservice.entities.Idea;

import java.io.Serializable;
import java.time.LocalDateTime;

@AllArgsConstructor
@Value
public class IdeaReadDto implements Serializable {
    Long id;
    String titre;
    String description;
    Idea.Status status;
    LocalDateTime createdAt;
    String feedback;
}