package com.example.brain2build.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

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

    @NotBlank
    @Size(max = 150)
    private String title;

    @NotBlank
    @Size(max = 1000)
    private String description;

    @Column(precision = 3, scale = 2)
    private Double aiScore;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.PENDING;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(length = 500)
    private String feedback;

    @ManyToOne
    @JoinColumn(name = "created_by")
    private Ideator createdBy;

    // Idea.java



    public enum Status {
        PENDING,
        APPROVED,
        REJECTED
    }


}

