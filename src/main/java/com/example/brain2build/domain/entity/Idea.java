package com.example.brain2build.domain.entity;

import com.example.brain2build.domain.dto.Ideator.IdeatorSimpleDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ideas")
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

    @ManyToOne
    @JoinColumn(name = "created_by_id")
    private Ideator createdBy;

    public enum Status {
        PENDING,
        APPROVED,
        REJECTED
    }
}

