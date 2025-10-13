package com.example.brain2build.domain.dto.Room;

import com.example.brain2build.domain.dto.Project.ProjectSimpleDto;
import com.example.brain2build.domain.dto.RoomMember.RoomMemberReadDto;
import jakarta.validation.constraints.NotBlank;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for {@link com.example.brain2build.domain.entity.Room}
 */
@Value
public class RoomReadDto implements Serializable {
    Long id;
    String roomName;
    int maxMembers;
    boolean isFull;
    LocalDateTime createdAt;
    ProjectSimpleDto project;
    List<RoomMemberReadDto> members;
}