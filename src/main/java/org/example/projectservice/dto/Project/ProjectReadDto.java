package org.example.projectservice.dto.Project;

import lombok.Value;
import org.example.projectservice.entity.Project;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * DTO de lecture de projet
 */
@Value
public class ProjectReadDto implements Serializable {

    Long id;
    String projectName;
    Project.Status status;
    LocalDateTime startDate;
    LocalDateTime endDate;

    // On expose seulement les IDs vers les autres microservices
    Set<Long> ideaIds;
    Long roomId;
}
