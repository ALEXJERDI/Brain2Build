package com.example.brain2build.domain.dto.Auth;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkerRegisterRequest {
    String email;
    String password;
    String nom;
    String prenom;
    String telephone;
    String domaine;       // Backend, Frontend, etc.
    String specialite;    // Java, React, etc.
    int experience;       // années
    String portfolioUrl;  // lien vers projets
}
