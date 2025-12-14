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
    String description;    // 🔥 ajouté

    Project.Status status;
    LocalDateTime startDate;
    LocalDateTime endDate;

    Long creatorId;        // 🔥 ajouté

    Set<Long> ideaIds;
    Long roomId;
}

