package com.example.brain2build.domain.dto.Idea;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.example.brain2build.domain.entity.Idea}
 */
@Value
public class IdeaSimpleDto implements Serializable {
    Long id;
    String titre;  // Keep "title" consistent with the other DTOs if you're following that naming convention
    String status;  // Status should be String if it's coming from the enum.
}
