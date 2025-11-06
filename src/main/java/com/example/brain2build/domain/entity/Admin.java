package com.example.brain2build.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("ADMIN")
public class Admin extends User {

    @Column(nullable = true)
    private String privilegeLevel; // SUPER_ADMIN, MANAGER...

    @Column(nullable = true)
    private String department; // ex: Validation, Moderation

    // Getters / Setters
    
}
