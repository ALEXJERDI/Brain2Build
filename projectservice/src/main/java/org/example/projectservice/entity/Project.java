package org.example.projectservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.time.LocalDateTime;
import java.util.Set;
@Entity
@Table(name = "projects")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String projectName;

    private String description;   // 🔥 ajouté

    private Long creatorId;       // 🔥 ajouté

    @Enumerated(EnumType.STRING)
    private Status status = Status.IN_PROGRESS;

    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @ElementCollection
    @CollectionTable(name = "project_ideas", joinColumns = @JoinColumn(name = "project_id"))
    @Column(name = "idea_id")
    private Set<Long> ideaIds;

    private Long roomId;

    private String sourceType;    // optional
    private Long sourceId;        // optional

    public enum Status {
        IN_PROGRESS,
        COMPLETED,
        ARCHIVED
    }
}
