package org.example.ideaservice.dto.request;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class RequestUpdateDto {
    private String titre;
    private String description;
    private Double budget;
    private LocalDateTime deadline;
}

