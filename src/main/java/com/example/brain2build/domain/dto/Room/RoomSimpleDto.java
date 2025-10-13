package com.example.brain2build.domain.dto.Room;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.example.brain2build.domain.entity.Room}
 */
@Value
public class RoomSimpleDto implements Serializable {
    Long id;
    String roomName;
    boolean isFull;
}