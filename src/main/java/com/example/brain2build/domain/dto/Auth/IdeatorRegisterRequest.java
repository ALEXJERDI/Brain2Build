package com.example.brain2build.domain.dto.Auth;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IdeatorRegisterRequest {
    private String email;
    private String password;
    private String nom;
    private String prenom;
    private String telephone;
    private String bio;
}

