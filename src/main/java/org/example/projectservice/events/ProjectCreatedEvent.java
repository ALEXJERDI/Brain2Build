package org.example.projectservice.events;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectCreatedEvent implements Serializable {
    private Long projectId;
    private String projectName;
    private Set<Long> ideaIds;
}
