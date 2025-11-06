package com.example.brain2build.domain.dto.RoomMember;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.example.brain2build.domain.entity.RoomMember}
 */
@Value
public class RoomMemberCreateUpdateDto implements Serializable {
    String roleInRoom;
    boolean lead;
    @NotNull(message = "L'identifiant du worker est obligatoire")
    Long workerId;
    Long roomId;



}