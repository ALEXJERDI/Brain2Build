package org.example.roomservice.Services.RoomMemberServices;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.roomservice.DTO.Roommemberdto.RoomMemberCreateUpdateDto;
import org.example.roomservice.DTO.Roommemberdto.RoomMemberReadDto;
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

    // Placeholder for future worker verification
    // private final UserService userService;

    @Override
    public RoomMemberReadDto addWorkerToRoom(RoomMemberCreateUpdateDto dto) {

        // Fetch the room from the database
        Room room = roomRepository.findById(dto.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found"));

        // Check if the room is already full
        if (room.isFull()) {
            throw new RuntimeException("Room is already full");
        }

        // Check if the worker is already a member of the room
        boolean alreadyMember = roomMemberRepository.existsByRoomAndWorkerId(room, dto.getWorkerId());
        if (alreadyMember) {
            throw new RuntimeException("Worker is already in this room");
        }

        // Here you should call User Service to verify if worker exists (future enhancement)
        // userService.verifyWorkerExistence(dto.getWorkerId());

        // Create the new room member and set the room
        RoomMember member = roomMemberMapper.toEntity(dto);
        member.setRoom(room);

        // Check if the first member is being added and set them as lead
        boolean isFirst = roomMemberRepository.countByRoom_Id(room.getId()) == 0;
        member.setLead(isFirst);

        // Save the new member
        RoomMember savedMember = roomMemberRepository.save(member);

        // After saving, check if the room has reached the max capacity
        long count = roomMemberRepository.countByRoom_Id(room.getId());
        if (count >= room.getMaxMembers()) {
            room.setFull(true);  // Mark the room as full if the count exceeds or matches maxMembers
            roomRepository.save(room);  // Save the room again to persist the 'full' status
        }

        return roomMemberMapper.toReadDto(savedMember);  // Return the created member as a DTO
    }

    @Override
    public void removeWorkerFromRoom(Long roomMemberId) {
        // Find the room member to remove
        RoomMember member = roomMemberRepository.findById(roomMemberId)
                .orElseThrow(() -> new RuntimeException("RoomMember not found"));

        Room room = member.getRoom();

        // Remove the member from the room
        roomMemberRepository.delete(member);

        // After deletion, check if the room is now not full and update its status
        long count = roomMemberRepository.countByRoom_Id(room.getId());
        if (room.isFull() && count < room.getMaxMembers()) {
            room.setFull(false);
            roomRepository.save(room);
        }
    }

    @Override
    public List<RoomMemberReadDto> getMembersByRoom(Long roomId) {
        // Fetch all members of the room and map them to DTOs
        return roomMemberRepository.findByRoom_Id(roomId)
                .stream()
                .map(roomMemberMapper::toReadDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<RoomMemberReadDto> getMembershipsByWorker(Long workerId) {
        // Fetch all memberships of the worker and map them to DTOs
        return roomMemberRepository.findByWorkerId(workerId)
                .stream()
                .map(roomMemberMapper::toReadDto)
                .collect(Collectors.toList());
    }

    @Override
    public RoomMemberReadDto promoteToLead(Long roomMemberId) {
        // Find the room member to promote
        RoomMember member = roomMemberRepository.findById(roomMemberId)
                .orElseThrow(() -> new NoSuchElementException("Room member not found"));

        // Check if the member is already a lead
        if (member.isLead()) {
            throw new IllegalStateException("This member is already a lead");
        }

        // Check if the room already has a lead
        boolean leadExists = roomMemberRepository.existsByRoom_IdAndLeadTrue(member.getRoom().getId());
        if (leadExists) {
            throw new IllegalStateException("This room already has a lead");
        }

        // Promote the member to lead
        member.setLead(true);

        // Save the updated member
        RoomMember savedMember = roomMemberRepository.saveAndFlush(member);

        return roomMemberMapper.toReadDto(savedMember);
    }
}
