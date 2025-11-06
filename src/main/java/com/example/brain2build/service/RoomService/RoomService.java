package com.example.brain2build.service.RoomService;


import com.example.brain2build.domain.dto.Room.*;
import com.example.brain2build.domain.dto.RoomMember.RoomMemberReadDto;

import java.util.List;

public interface RoomService {
    RoomReadDto createRoom(RoomCreateUpdateDto dto);
    RoomReadDto getRoomById(Long id);
    List<RoomReadDto> getAllRooms();
    RoomReadDto updateRoom(Long id, RoomUpdateDto dto);
    void deleteRoom(Long id);

    List<RoomMemberReadDto> getMembersInRoom(Long roomId);
    RoomReadDto getRoomByProjectId(Long projectId);
}

