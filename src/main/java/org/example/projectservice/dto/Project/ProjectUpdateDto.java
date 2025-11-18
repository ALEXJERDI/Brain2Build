package org.example.projectservice.dto.Project;

import lombok.Value;

import java.io.Serializable;
import java.util.Set;

/**
 * DTO pour mise à jour partielle d'un projet
 */
@Value
public class ProjectUpdateDto implements Serializable {

    // optionnel – seulement si tu veux le changer
    String projectName;

    // optionnel – seulement si tu veux changer les idées liées
    Set<Long> ideaIds;
}
