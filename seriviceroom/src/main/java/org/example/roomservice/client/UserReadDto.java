package org.example.roomservice.client;

import lombok.Data;

@Data
public class UserReadDto {
    private Long id;
    private String email;
    private String nom;
    private String prenom;
    private String telephone;
    private String userType;  // worker / client / ideator
}