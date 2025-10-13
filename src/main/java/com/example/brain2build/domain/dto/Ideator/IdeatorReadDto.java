package com.example.brain2build.domain.dto.Ideator;

import com.example.brain2build.domain.dto.Idea.IdeaSimpleDto;
import lombok.Value;

import java.io.Serializable;
import java.util.List;

/**
 * DTO for {@link com.example.brain2build.domain.entity.Ideator}
 */
@Value
public class IdeatorReadDto implements Serializable {
    Long id;
    String email;
    String nom;
    String prenom;
    String telephone;
    String bio;
    int ideaCount;
    List<IdeaSimpleDto> ideas;
}