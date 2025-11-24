package org.example.roomservice.DTO.Roomdto;




import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import org.example.roomservice.DTO.Roommemberdto.RoomMemberReadDto;

@Data                       // getters / setters / toString / equals
@NoArgsConstructor          // 👈 IMPORTANT pour MapStruct (new RoomReadDto())
@AllArgsConstructor         // pratique si tu veux construire à la main
public class RoomReadDto implements Serializable {

    private Long id;
    private String roomName;
    private int maxMembers;
    private boolean full;
    private LocalDateTime createdAt;

    private Long projectId;                       // plus de ProjectSimpleDto
    private List<RoomMemberReadDto> members;
}
