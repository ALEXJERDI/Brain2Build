package org.example.userservice.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ideator_profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IdeatorProfile {

    @Id
    private Long userId; // PK = FK vers User

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    @Column(length = 255)
    private String bio;

    @Column(nullable = false)
    private int ideaCount;
}
