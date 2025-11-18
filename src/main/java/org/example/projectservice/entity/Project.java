package org.example.projectservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Set;

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

    // Microservices-friendly : on stocke seulement les IDs des idées
    @ElementCollection
    @CollectionTable(name = "project_ideas", joinColumns = @JoinColumn(name = "project_id"))
    @Column(name = "idea_id")
    private Set<Long> ideaIds;

    // ID du Room dans un autre microservice
    private Long roomId;

    public enum Status {
        IN_PROGRESS,
        COMPLETED,
        ARCHIVED
    }
}
