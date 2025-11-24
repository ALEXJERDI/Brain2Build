package org.example.roomservice.Services.RoomServices;





import org.example.roomservice.DTO.Roomdto.RoomCreateDto;
import org.example.roomservice.DTO.Roomdto.RoomReadDto;
import org.example.roomservice.DTO.Roomdto.RoomUpdateDto;
import org.example.roomservice.DTO.Roommemberdto.RoomMemberReadDto;

import java.util.List;

public interface RoomService {

    RoomReadDto createRoom(RoomCreateDto dto);

    RoomReadDto getRoomById(Long id);

    List<RoomReadDto> getAllRooms();

    RoomReadDto updateRoom(Long id, RoomUpdateDto dto);

    void deleteRoom(Long id);

    List<RoomMemberReadDto> getMembersInRoom(Long roomId);

    RoomReadDto getRoomByProjectId(Long projectId);
}

