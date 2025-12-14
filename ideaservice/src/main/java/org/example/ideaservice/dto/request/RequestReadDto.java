package org.example.ideaservice.dto.request;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class RequestReadDto {
    private Long id;
    private String titre;
    private String description;
    private String status;
    private LocalDateTime createdAt;
    private Long creatorId;

    private Double budget;
    private LocalDateTime deadline;
}
