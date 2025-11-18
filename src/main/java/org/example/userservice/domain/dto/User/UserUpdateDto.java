package org.example.userservice.domain.dto.User;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserUpdateDto {

    private String email;
    private String nom;
    private String prenom;
    private String telephone;
}
