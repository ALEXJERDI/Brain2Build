package org.example.projectservice.dto.Project;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;
import java.util.Set;

/**
 * DTO de création/mise à jour de projet
 */
@Value
public class ProjectCreateUpdateDto implements Serializable {

    @NotBlank(message = "Le titre du projet est obligatoire")
    @Size(max = 150)
    String projectName;

    // IDs des idées liées
    Set<Long> ideaIds;
}
