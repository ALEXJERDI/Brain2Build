package org.example.projectservice.dto.Project;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO simplifié (si tu en as besoin pour des listes légères)
 */
@Value
public class ProjectSimpleDto implements Serializable {
    Long id;
    String projectName;
    String status;
}
