package com.example.brain2build.domain.dto.Project;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;
import java.util.Set;

/**
 * DTO for {@link com.example.brain2build.domain.entity.Project}
 */
@Value
public class ProjectCreateUpdateDto implements Serializable {
    @NotBlank(message = "Le titre du projet est obligatoire")
    @Size(max = 150)
    String projectName;
    Set<Long> ideaIds;
}