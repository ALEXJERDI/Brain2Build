package org.example.ideaservice.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "ideas")
public class Idea {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;  // Title of the idea
    private String description;  // Description of the idea

    @Enumerated(EnumType.STRING)
    private Status status;  // Status of the idea (PENDING, APPROVED, REJECTED)

    private LocalDateTime createdAt;  // Timestamp of creation

    @Column(length = 500)
    private String feedback;  // Feedback for the idea

    @Column(name = "created_by_id")
    private Long createdById;  // Store only the ID of the user who created the idea

    // Getter and Setter methods for all fields

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public Long getCreatedById() {
        return createdById;
    }

    public void setCreatedById(Long createdById) {
        this.createdById = createdById;
    }

    // Enum for Status
    public enum Status {
        PENDING,
        APPROVED,
        REJECTED
    }
}
