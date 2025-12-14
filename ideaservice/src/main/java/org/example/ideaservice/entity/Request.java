package org.example.ideaservice.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "requests")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;
    private String description;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime createdAt;

    @Column(name = "creator_id")
    private Long creatorId;

    private Double budget;        // optional
    private LocalDateTime deadline; // optional

    public enum Status {
        PENDING,
        ACTIVE,
        CLOSED,
        REJECTED
    }
}
