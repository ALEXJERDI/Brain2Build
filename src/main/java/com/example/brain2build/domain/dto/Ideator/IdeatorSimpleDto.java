package com.example.brain2build.domain.dto.Ideator;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.example.brain2build.domain.entity.Ideator}
 */
@Value
public class IdeatorSimpleDto implements Serializable {
    Long id;
    String nom;
    String prenom;
    int ideaCount;
}