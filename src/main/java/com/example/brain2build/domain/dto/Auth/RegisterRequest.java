package com.example.brain2build.domain.dto.Auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO utilisé pour l'inscription d'un nouvel utilisateur.
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
    private String bio; // facultatif, utilisé si ideator
}
