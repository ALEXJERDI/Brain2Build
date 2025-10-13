package com.example.brain2build.domain.dto.Idea;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.example.brain2build.domain.entity.Idea}
 */
@Value
public class IdeaCreateUpdateDto implements Serializable {
    @NotBlank(message = "Le titre est obligatoire")
    @Size(max = 150, message = "Le titre ne doit pas dépasser 150 caractères")
    String title;
    @NotBlank(message = "La description est obligatoire")
    @Size(max = 1000, message = "La description ne doit pas dépasser 1000 caractères")
    String description;
}