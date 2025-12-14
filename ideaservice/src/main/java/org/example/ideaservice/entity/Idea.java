package org.example.ideaservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "ideas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Idea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;
    private String description;

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDateTime createdAt;

    @Column(length = 500)
    private String feedback;

    // IMPORTANT: plus de relation User
    @Column(name = "creator_id")
    private Long creatorId;

    public enum Status {
        PENDING,
        APPROVED,
        REJECTED
    }
}
