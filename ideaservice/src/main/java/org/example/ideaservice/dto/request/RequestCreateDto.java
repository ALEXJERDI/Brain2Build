package org.example.ideaservice.dto.request;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class RequestCreateDto {
    private String titre;
    private String description;
    private Long creatorId; // user-service id

    private Double budget;
    private LocalDate deadline;
}
