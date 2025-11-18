package org.example.userservice.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "worker_profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkerProfile {

    @Id
    private Long userId; // PK = FK vers User

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private String domaine;       // Backend, Frontend...

    @Column
    private String specialite;    // Java, React...

    @Column
    private Integer experience;   // en années

    @Column
    private String portfolioUrl;
}
