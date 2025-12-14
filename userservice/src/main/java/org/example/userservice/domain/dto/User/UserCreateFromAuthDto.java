package org.example.userservice.domain.dto.User;

import lombok.Data;

@Data
public class UserCreateFromAuthDto {

    // info de base
    private String email;
    private String nom;
    private String prenom;
    private String telephone;

    // rôle métier = "WORKER" ou "IDEATOR"
    private String role;

    // Champs spécifiques IDEATOR
    private String bio;

    // Champs spécifiques WORKER
    private String domaine;
    private String specialite;
    private Integer experience;
    private String portfolioUrl;
}
