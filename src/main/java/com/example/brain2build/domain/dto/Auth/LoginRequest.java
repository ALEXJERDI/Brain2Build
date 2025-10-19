package com.example.brain2build.domain.dto.Auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO utilisé pour la connexion d'un utilisateur.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    private String email;
    private String password;
}
