package com.example.brain2build.domain.dto.Idea;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.example.brain2build.domain.entity.Idea}
 */
@Value
public class IdeaSimpleDto implements Serializable {
    Long id;
    String title;
    String status;
}