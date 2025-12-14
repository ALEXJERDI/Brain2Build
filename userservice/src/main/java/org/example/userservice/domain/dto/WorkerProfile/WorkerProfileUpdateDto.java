package org.example.userservice.domain.dto.WorkerProfile;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkerProfileUpdateDto {

    private String domaine;
    private String specialite;
    private Integer experience;
    private String portfolioUrl;
}


