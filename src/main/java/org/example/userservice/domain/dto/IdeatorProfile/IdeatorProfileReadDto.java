package org.example.userservice.domain.dto.IdeatorProfile;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IdeatorProfileReadDto {

    private Long userId;
    private String email;
    private String nom;
    private String prenom;

    private String bio;
    private int ideaCount;
}


