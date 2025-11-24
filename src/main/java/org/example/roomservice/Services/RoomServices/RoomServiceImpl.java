package org.example.roomservice.Services.RoomServices;




import lombok.RequiredArgsConstructor;
import org.example.roomservice.DTO.Roomdto.RoomCreateDto;
import org.example.roomservice.DTO.Roomdto.RoomReadDto;
import org.example.roomservice.DTO.Roomdto.RoomUpdateDto;
import org.example.roomservice.DTO.Roommemberdto.RoomMemberReadDto;
import org.example.roomservice.entity.Room;
import org.example.roomservice.mappers.RoomMapper;
import org.example.roomservice.mappers.RoomMemberMapper;
import org.example.roomservice.repositorys.RoomMemberRepository;
import org.example.roomservice.repositorys.RoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final RoomMemberRepository roomMemberRepository;
    private final RoomMapper roomMapper;
    private final RoomMemberMapper roomMemberMapper;

    // ❌ SUPPRIMÉ (monolithique) :
    // private final ProjectRepository projectRepository;

    // ❌ SUPPRIMÉ :
    // Room.setProject(Project)

    @Override
    public RoomReadDto createRoom(RoomCreateDto dto) {

        // 1️⃣ Vérifier si ce projet possède déjà une room
        if (roomRepository.existsByProjectId(dto.getProjectId())) {
            throw new IllegalStateException("Ce projet possède déjà une room.");
        }

        // 2️⃣ Normaliser le nom pour éviter les doublons (case-insensitive + trim)
        String normalizedRoomName = dto.getRoomName().trim().toLowerCase();

        if (roomRepository.existsByRoomNameIgnoreCase(normalizedRoomName)) {
            throw new IllegalStateException(
                    "Une room portant le nom '" + dto.getRoomName() + "' existe déjà."
            );
        }

        // 3️⃣ Map DTO → Entity
        Room room = roomMapper.toEntity(dto);

        room.setFull(false);
        room.setRoomName(dto.getRoomName().trim()); // propre et standardisé

        // 4️⃣ Sauvegarde
        Room saved = roomRepository.save(room);

        // 5️⃣ Retour du DTO lecture
        return roomMapper.toReadDto(saved);
    }


    @Override
    public RoomReadDto getRoomById(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Room not found"));
        return roomMapper.toReadDto(room);
    }

    @Override
    public List<RoomReadDto> getAllRooms() {
        return roomRepository.findAll()
                .stream()
                .map(roomMapper::toReadDto)
                .collect(Collectors.toList());
    }

    @Override
    public RoomReadDto updateRoom(Long id, RoomUpdateDto dto) {

        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Room not found"));

        // 🟦 CHANGEMENT MICRO-SERVICE :
        // Mise à jour partielle (nom + maxMembers)
        roomMapper.partialUpdate(dto, room);

        // 🟦 CHANGEMENT MICRO-SERVICE :
        // Pas de vérification du projet via repository local
        if (dto.getProjectId() != null &&
                !dto.getProjectId().equals(room.getProjectId())) {

            if (roomRepository.existsByProjectId(dto.getProjectId())) {
                throw new IllegalStateException("Ce nouveau projet a déjà une room.");
            }

            room.setProjectId(dto.getProjectId());
        }

        Room updated = roomRepository.save(room);
        return roomMapper.toReadDto(updated);
    }

    @Override
    public void deleteRoom(Long id) {
        if (!roomRepository.existsById(id)) {
            throw new RuntimeException("Room not found");
        }
        roomRepository.deleteById(id);
    }

    @Override
    public List<RoomMemberReadDto> getMembersInRoom(Long roomId) {
        return roomMemberRepository.findByRoom_Id(roomId)
                .stream()
                .map(roomMemberMapper::toReadDto)
                .collect(Collectors.toList());
    }

    @Override
    public RoomReadDto getRoomByProjectId(Long projectId) {
        Room room = roomRepository.findByProjectId(projectId)
                .orElseThrow(() -> new RuntimeException("Room not found"));
        return roomMapper.toReadDto(room);
    }
}

