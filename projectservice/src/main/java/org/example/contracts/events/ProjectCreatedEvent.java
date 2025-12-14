package org.example.contracts.events;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectCreatedEvent {
    private Long projectId;
    private String projectName;
    private String description;
    private Long creatorId;

    // Optionnel selon ton modèle
    private Set<Long> ideaIds;    // si ton Project garde des idées
    private String sourceType;    // "IDEA" ou "REQUEST"
    private Long sourceId;        // ideaId ou requestId
}