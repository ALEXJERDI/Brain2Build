package com.example.brain2build.service.RoomMemberService;


import com.example.brain2build.domain.dto.RoomMember.*;
import com.example.brain2build.domain.entity.Room;
import com.example.brain2build.domain.entity.RoomMember;
import com.example.brain2build.domain.entity.Worker;
import com.example.brain2build.mappers.RoomMemberMapper;
import com.example.brain2build.repository.RoomMemberRepository;
import com.example.brain2build.repository.RoomRepository;
import com.example.brain2build.repository.WorkerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RoomMemberServiceImpl implements RoomMemberService {

    private final RoomMemberRepository roomMemberRepository;
    private final RoomRepository roomRepository;
    private final WorkerRepository workerRepository;
    private final RoomMemberMapper roomMemberMapper;

    @Override
    public RoomMemberReadDto addWorkerToRoom(RoomMemberCreateUpdateDto dto) {
        Room room = roomRepository.findById(dto.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found"));
        Worker worker = workerRepository.findById(dto.getWorkerId())
                .orElseThrow(() -> new RuntimeException("Worker not found"));

        if (room.isFull()) throw new RuntimeException("Room is already full");

        boolean alreadyMember = roomMemberRepository.existsByRoomAndWorker(room, worker);
        if (alreadyMember) throw new RuntimeException("Worker already joined this room");

        RoomMember member = roomMemberMapper.toEntity(dto);
        member.setWorker(worker);
        member.setRoom(room);
        member.setLead(room.getMembers().isEmpty());

        RoomMember saved = roomMemberRepository.save(member);

        if (room.getMembers().size() >= room.getMaxMembers()) {
            room.setFull(true);
            roomRepository.save(room);
        }

        return roomMemberMapper.toReadDto(saved);
    }
/// verifier  que lid connecter  est ce qu il est un lead si il est un lead il vas pouvoir suprimmer et verifier  si il existe dans la room
    @Override
    public void removeWorkerFromRoom(Long roomMemberId) {
        RoomMember member = roomMemberRepository.findById(roomMemberId)
                .orElseThrow(() -> new RuntimeException("RoomMember not found"));

        Room room = member.getRoom();
        roomMemberRepository.delete(member);

        if (room.isFull()) {
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
        return roomMemberRepository.findByWorker_Id(workerId)
                .stream()
                .map(roomMemberMapper::toReadDto)
                .collect(Collectors.toList());
    }

    @Override
    public RoomMemberReadDto promoteToLead(Long roomMemberId) {
        // 1️⃣ Find the member by ID
        RoomMember member = roomMemberRepository.findById(roomMemberId)
                .orElseThrow(() -> new NoSuchElementException("Room member not found"));

        // 2️⃣ Optional safety checks
        if (member.isLead()) {
            throw new IllegalStateException("This member is already a lead.");
        }

        // Prevent multiple leads in the same room (if required by your business logic)
        boolean anotherLeadExists = roomMemberRepository.existsByRoom_IdAndLeadTrue(member.getRoom().getId());
        if (anotherLeadExists) {
            throw new IllegalStateException("This room already has a lead.");
        }

        // 3️⃣ Promote
        member.setLead(true);

        // 4️⃣ Persist changes
        RoomMember updated = roomMemberRepository.saveAndFlush(member);

        // 5️⃣ Return the read DTO
        return roomMemberMapper.toReadDto(updated);
    }

}
