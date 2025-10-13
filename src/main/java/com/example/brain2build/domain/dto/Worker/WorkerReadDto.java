package com.example.brain2build.domain.dto.Worker;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.example.brain2build.domain.entity.Worker}
 */
@Value
public class WorkerReadDto implements Serializable {
    Long id;
    String email;
    String nom;
    String prenom;
    String telephone;
    String domaine;
    String specialite;
    int experience;
    String portfolioUrl;
}