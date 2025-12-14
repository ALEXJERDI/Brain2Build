package org.example.contracts.events;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestActivatedEvent {
    private Long requestId;
    private Long creatorId;
    private String titre;
    private String description;
    private Double budget;
}
