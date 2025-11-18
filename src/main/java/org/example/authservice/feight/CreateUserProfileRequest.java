package org.example.authservice.feight;

import lombok.Data;

@Data
public class CreateUserProfileRequest {

    private Long authUserId;   // id généré dans AuthUser

    private String email;
    private String nom;
    private String prenom;
    private String telephone;

    private String role; // WORKER / IDEATOR

    // Champs IDEATOR
    private String bio;

    // Champs WORKER
    private String domaine;
    private String specialite;
    private Integer experience;
    private String portfolioUrl;
}
