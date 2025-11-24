package org.example.roomservice.DTO.Roommemberdto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomMemberReadDto implements Serializable {

    private Long id;
    private String roleInRoom;
    private boolean lead;
    private LocalDateTime joinedAt;
    private Long workerId;
}
