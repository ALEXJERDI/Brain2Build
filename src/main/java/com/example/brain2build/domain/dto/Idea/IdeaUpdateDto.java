package com.example.brain2build.domain.dto.Idea;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
public class IdeaUpdateDto {
    @Size(max = 150, message = "Le titre ne doit pas dépasser 150 caractères")
    String titre;  // Consistent with the entity field name
    @Size(max = 1000, message = "La description ne doit pas dépasser 1000 caractères")
    String description;
}
