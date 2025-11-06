package com.example.brain2build.domain.dto.RoomMember;

import com.example.brain2build.domain.dto.Room.RoomSimpleDto;
import com.example.brain2build.domain.dto.Worker.WorkerSimpleDto;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.example.brain2build.domain.entity.RoomMember}
 */
@Value
public class RoomMemberReadDto implements Serializable {
    Long id;
    String roleInRoom;
    boolean lead;
    LocalDateTime joinedAt;
    WorkerSimpleDto worker;
}