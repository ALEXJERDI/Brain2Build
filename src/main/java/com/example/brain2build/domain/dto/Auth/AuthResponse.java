package com.example.brain2build.domain.dto.Auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO renvoyé après l'authentification (login ou register).
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {

    private String token;
}
