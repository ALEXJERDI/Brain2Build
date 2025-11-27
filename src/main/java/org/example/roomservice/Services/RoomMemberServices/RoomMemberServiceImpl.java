package org.example.roomservice.Services.RoomMemberServices;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.roomservice.DTO.Roommemberdto.RoomMemberCreateUpdateDto;
import org.example.roomservice.DTO.Roommemberdto.RoomMemberReadDto;
import org.example.roomservice.client.UserClient;
import org.example.roomservice.client.UserReadDto;
import org.example.roomservice.entity.Room;
import org.example.roomservice.entity.RoomMember;
import org.example.roomservice.mappers.RoomMemberMapper;
import org.example.roomservice.repositorys.RoomMemberRepository;
import org.example.roomservice.repositorys.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RoomMemberServiceImpl implements RoomMemberService {

    private final RoomMemberRepository roomMemberRepository;
    private final RoomRepository roomRepository;
    private final RoomMemberMapper roomMemberMapper;
    private final UserClient userClient; // FEIGN

    @Override
    public RoomMemberReadDto joinRoom(RoomMemberCreateUpdateDto dto) {

        // 1️⃣ Vérifier si le worker existe via user-service
        UserReadDto user = userClient.getUserById(dto.getWorkerId());

        // 2️⃣ Vérifier que c’est bien un WORKER (règle métier fondamentale)
        if (!"WORKER".equalsIgnoreCase(user.getUserType())) {
            throw new RuntimeException("Only workers can join a room.");
        }

        // 3️⃣ Vérifier la Room
        Room room = roomRepository.findById(dto.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found"));

        if (room.isFull()) {
            throw new RuntimeException("Room is already full");
        }

        // 4️⃣ Vérifier si le worker est déjà membre
        if (roomMemberRepository.existsByRoomAndWorkerId(room, dto.getWorkerId())) {
            throw new RuntimeException("Worker already in this room");
        }

        // 5️⃣ Mapper + créer
        RoomMember member = roomMemberMapper.toEntity(dto);
        member.setRoom(room);
        member.setLead(false); // jamais auto-lead

        RoomMember saved = roomMemberRepository.save(member);

        // 6️⃣ Vérifier si la room doit devenir full
        long count = roomMemberRepository.countByRoom_Id(room.getId());
        if (count >= room.getMaxMembers()) {
            room.setFull(true);
            roomRepository.save(room);
        }

        return roomMemberMapper.toReadDto(saved);
    }

    @Override
    public void removeWorkerFromRoom(Long roomMemberId) {

        RoomMember member = roomMemberRepository.findById(roomMemberId)
                .orElseThrow(() -> new RuntimeException("RoomMember not found"));

        Room room = member.getRoom();
        roomMemberRepository.delete(member);

        long count = roomMemberRepository.countByRoom_Id(room.getId());

        if (room.isFull() && count < room.getMaxMembers()) {
            room.setFull(false);
            roomRepository.save(room);
        }
    }

    @Override
    public List<RoomMemberReadDto> getMembersByRoom(Long roomId) {
        return roomMemberRepository.findByRoom_Id(roomId)
                .stream()
                .map(roomMemberMapper::toReadDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<RoomMemberReadDto> getMembershipsByWorker(Long workerId) {
        return roomMemberRepository.findByWorkerId(workerId)
                .stream()
                .map(roomMemberMapper::toReadDto)
                .collect(Collectors.toList());
    }

    @Override
    public RoomMemberReadDto promoteToLead(Long roomMemberId) {

        RoomMember member = roomMemberRepository.findById(roomMemberId)
                .orElseThrow(() -> new RuntimeException("RoomMember not found"));

        if (member.isLead()) {
            throw new IllegalStateException("Already lead");
        }

        if (roomMemberRepository.existsByRoom_IdAndLeadTrue(member.getRoom().getId())) {
            throw new IllegalStateException("Room already has a lead");
        }

        member.setLead(true);

        RoomMember saved = roomMemberRepository.saveAndFlush(member);

        return roomMemberMapper.toReadDto(saved);
    }
}
