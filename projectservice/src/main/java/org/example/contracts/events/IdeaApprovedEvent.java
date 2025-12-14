package org.example.contracts.events;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class IdeaApprovedEvent {
    private Long ideaId;
    private Long creatorId;
    private String titre;
    private String description;
}
