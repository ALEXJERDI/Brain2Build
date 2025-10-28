package com.example.brain2build.service.RoomMemberService;


import com.example.brain2build.domain.dto.RoomMember.*;

import java.util.List;

public interface RoomMemberService {
    RoomMemberReadDto addWorkerToRoom(RoomMemberCreateUpdateDto dto);
    void removeWorkerFromRoom(Long roomMemberId);
    List<RoomMemberReadDto> getMembersByRoom(Long roomId);
    List<RoomMemberReadDto> getMembershipsByWorker(Long workerId);
    RoomMemberReadDto promoteToLead(Long roomMemberId);
}
