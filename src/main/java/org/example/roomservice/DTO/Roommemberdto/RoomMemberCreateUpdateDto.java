package org.example.roomservice.DTO.Roommemberdto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomMemberCreateUpdateDto implements Serializable {

    private String roleInRoom;
    private boolean lead;

    @NotNull(message = "L'identifiant du worker est obligatoire")
    private Long workerId;

    private Long roomId;
}
