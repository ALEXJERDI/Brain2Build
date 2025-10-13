package com.example.brain2build.domain.dto.Project;

import com.example.brain2build.domain.dto.Idea.IdeaSimpleDto;
import com.example.brain2build.domain.dto.Room.RoomSimpleDto;
import com.example.brain2build.domain.entity.Project;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

/**
 * DTO for {@link com.example.brain2build.domain.entity.Project}
 */
@Value
public class ProjectReadDto implements Serializable {
    Long id;
    String projectName;
    Project.Status status;
    LocalDateTime startDate;
    LocalDateTime endDate;
    Set<IdeaSimpleDto> ideas;
    RoomSimpleDto room;

}