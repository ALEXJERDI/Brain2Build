package org.example.userservice.domain.dto.User;

import lombok.*;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserReadDto {

    private Long id;
    private String email;
    private String nom;
    private String prenom;
    private String telephone;
    private String userType;
    private Instant createdAt;
}
