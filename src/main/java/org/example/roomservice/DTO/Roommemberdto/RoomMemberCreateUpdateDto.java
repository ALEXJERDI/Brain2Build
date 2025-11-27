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

    @NotNull(message = "L'identifiant du worker est obligatoire")
    private Long workerId;

    @NotNull(message = "L'identifiant de la room est obligatoire")
    private Long roomId;
}
