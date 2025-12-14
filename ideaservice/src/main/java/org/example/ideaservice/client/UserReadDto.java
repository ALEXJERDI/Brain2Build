package org.example.ideaservice.client;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class UserReadDto {
    private Long id;
    private String email;
    private String nom;
    private String prenom;
    private String telephone;
    private String userType;   // IDEATOR / WORKER / ADMIN ...
    private Instant createdAt;
}

