package org.example.roomservice.Services.RoomMemberServices;



import org.example.roomservice.DTO.Roommemberdto.RoomMemberCreateUpdateDto;
import org.example.roomservice.DTO.Roommemberdto.RoomMemberReadDto;

import java.util.List;

public interface RoomMemberService {

    RoomMemberReadDto addWorkerToRoom(RoomMemberCreateUpdateDto dto);

    void removeWorkerFromRoom(Long roomMemberId);

    List<RoomMemberReadDto> getMembersByRoom(Long roomId);

    List<RoomMemberReadDto> getMembershipsByWorker(Long workerId);

    RoomMemberReadDto promoteToLead(Long roomMemberId);
}
