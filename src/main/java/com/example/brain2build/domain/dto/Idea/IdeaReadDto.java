package com.example.brain2build.domain.dto.Idea;

import com.example.brain2build.domain.dto.Ideator.IdeatorSimpleDto;
import com.example.brain2build.domain.entity.Idea;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.example.brain2build.domain.entity.Idea}
 */
@Value
public class IdeaReadDto implements Serializable {
    Long id;
    String title;
    String description;
    Double aiScore;
    Idea.Status status;
    LocalDateTime createdAt;
    IdeatorSimpleDto createdBy;
}