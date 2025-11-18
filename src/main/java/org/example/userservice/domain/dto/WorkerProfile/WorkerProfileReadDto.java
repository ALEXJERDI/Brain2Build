package org.example.userservice.domain.dto.WorkerProfile;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkerProfileReadDto {

    private Long userId;
    private String email;
    private String nom;
    private String prenom;

    private String domaine;
    private String specialite;
    private Integer experience;
    private String portfolioUrl;
}


