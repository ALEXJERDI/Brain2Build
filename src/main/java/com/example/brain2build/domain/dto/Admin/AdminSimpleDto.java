package com.example.brain2build.domain.dto.Admin;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.example.brain2build.domain.entity.Admin}
 */
@Value
public class AdminSimpleDto implements Serializable {
    Long id;
    String email;
    String nom;
    String prenom;
    String privilegeLevel;
}