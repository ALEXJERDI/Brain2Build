package org.example.authservice.domain.dto.Auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO générique pour l'inscription.
 * Champ `role` détermine si c'est un WORKER ou un IDEATOR.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String email;
    private String password;
    private String nom;
    private String prenom;
    private String telephone;

    private String role;   // "WORKER" or "IDEATOR"

    private String bio;

    private String domaine;
    private String specialite;
    private Integer experience;
    private String portfolioUrl;
}

