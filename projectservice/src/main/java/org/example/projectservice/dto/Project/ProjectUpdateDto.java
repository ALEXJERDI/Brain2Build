package org.example.projectservice.dto.Project;

import lombok.Value;

import java.io.Serializable;
import java.util.Set;

/**
 * DTO pour mise à jour partielle d'un projet
 */
@Value
public class ProjectUpdateDto implements Serializable {

    String projectName;
    String description;   // 🔥 ajouté

    Set<Long> ideaIds;
}

