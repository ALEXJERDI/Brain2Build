package org.example.authservice.feight;

import lombok.Data;

@Data
public class UserResponse {
    private Long id;
    private String email;
    private String nom;
    private String prenom;
    private String telephone;
    private String userType;
}

