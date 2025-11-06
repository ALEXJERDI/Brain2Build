package com.example.brain2build.service.RoomService;

import com.example.brain2build.domain.dto.Room.*;
import com.example.brain2build.domain.dto.RoomMember.RoomMemberReadDto;
import com.example.brain2build.domain.entity.Project;
import com.example.brain2build.domain.entity.Room;
import com.example.brain2build.mappers.RoomMapper;
import com.example.brain2build.mappers.RoomMemberMapper;
import com.example.brain2build.repository.ProjectRepository;
import com.example.brain2build.repository.RoomRepository;
import com.example.brain2build.repository.RoomMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final ProjectRepository projectRepository;
    private final RoomMemberRepository roomMemberRepository;
    private final RoomMapper roomMapper;
    private final RoomMemberMapper roomMemberMapper;

    @Override
    public RoomReadDto createRoom(RoomCreateDto dto) {
        // Mapper DTO → Entity
        Room room = roomMapper.toEntity(dto);

        if (dto.getProjectId() != null) {
            Project project = projectRepository.findById(dto.getProjectId())
                    .orElseThrow(() -> new RuntimeException("Project not found"));

            // Vérifie si ce projet a déjà une room associée
            boolean alreadyHasRoom = roomRepository.existsByProject_Id(dto.getProjectId());
            if (alreadyHasRoom) {
                throw new IllegalStateException("Ce projet est déjà associé à une room existante.");
            }

            room.setProject(project);
        }

        // Sauvegarde la nouvelle room
        Room savedRoom = roomRepository.save(room);
        return roomMapper.toReadDto(savedRoom);
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

        // ✅ Partial update des attributs simples (nom, maxMembers…)
        roomMapper.partialUpdate(dto, room);

        // ✅ Si un nouvel ID de projet est fourni, on change uniquement l’association
        if (dto.getProjectId() != null) {
            Project newProject = projectRepository.findById(dto.getProjectId())
                    .orElseThrow(() -> new NoSuchElementException("Project not found"));

            // 🔍 Vérifie si ce projet est déjà associé à une autre room
            boolean projectAlreadyLinked = roomRepository.existsByProject_Id(dto.getProjectId())
                    && (room.getProject() == null || !room.getProject().getId().equals(dto.getProjectId()));

            if (projectAlreadyLinked) {
                throw new IllegalStateException("This project is already assigned to another room.");
            }

            // ✅ Met à jour uniquement l'association du projet
            room.setProject(newProject);
        }

        // ⚙️ Sauvegarde finale
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
        Room room = roomRepository.findByProject_Id(projectId)
                .orElseThrow(() -> new RuntimeException("Room not found for this project"));
        return roomMapper.toReadDto(room);
    }
}
