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

    @NotBlank
    @Size(max = 150)
    String projectName;

    String description;   // 🔥 ajouté

    Long creatorId;       // 🔥 ajouté

    Set<Long> ideaIds;    // ok
}
