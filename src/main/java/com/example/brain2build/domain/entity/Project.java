package com.example.brain2build.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
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
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String projectName;

    @Enumerated(EnumType.STRING)
    private Status status = Status.IN_PROGRESS;

    @CreationTimestamp
    private LocalDateTime startDate;

    private LocalDateTime endDate;

    // Project.java
    @ManyToMany
    @JoinTable(
            name = "project_idea",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "idea_id")
    )
    private Set<Idea> ideas;


    public enum Status {
        IN_PROGRESS,
        COMPLETED,
        ARCHIVED
    }


}

