package com.example.brain2build.domain.dto.Worker;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.example.brain2build.domain.entity.Worker}
 */
@Value
public class WorkerSimpleDto implements Serializable {
    Long id;
    String nom;
    String prenom;
    String specialite;
}