package com.example.brain2build.domain.dto.Idea;

import com.example.brain2build.domain.dto.Ideator.IdeatorSimpleDto;
import com.example.brain2build.domain.entity.Idea;
import lombok.AllArgsConstructor;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.example.brain2build.domain.entity.Idea}
 */
@AllArgsConstructor
@Value
public class IdeaReadDto implements Serializable {
    Long id;
    String titre;  // Be sure this matches the entity's field names
    String description;
    Idea.Status status; // Match this directly with the enum from the entity
    LocalDateTime createdAt;
    IdeatorSimpleDto createdBy;
    String feedback;
}

