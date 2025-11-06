package com.example.brain2build.service.worker;

import com.example.brain2build.domain.dto.Project.ProjectReadDto;
import com.example.brain2build.domain.dto.Worker.*;
import com.example.brain2build.domain.dto.RoomMember.RoomMemberReadDto;
import com.example.brain2build.domain.entity.*;
import com.example.brain2build.mappers.ProjectMapper;
import com.example.brain2build.mappers.WorkerMapper;
import com.example.brain2build.mappers.RoomMemberMapper;
import com.example.brain2build.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class WorkerServiceImpl implements WorkerService {

    private final WorkerRepository workerRepository;
    private final RoomRepository roomRepository;
    private final RoomMemberRepository roomMemberRepository;
    private final WorkerMapper workerMapper;
    private final ProjectMapper projectMapper;
    private final RoomMemberMapper roomMemberMapper;

    /**
     * 🔍 Récupérer un worker par ID.
     */
    @Override
    public WorkerReadDto getWorkerById(Long id) {
        Worker worker = workerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Worker not found"));
        return workerMapper.toReadDto(worker);
    }

    /**
     * 📋 Lister tous les workers.
     */
    @Override
    public List<WorkerReadDto> getAllWorkers() {
        return workerRepository.findAll()
                .stream()
                .map(workerMapper::toReadDto)
                .collect(Collectors.toList());
    }

    /**
     * 🧭 Un worker rejoint un Room (crée un RoomMember).
     */
    @Override
    public RoomMemberReadDto joinRoom(Long workerId, Long roomId, String roleInRoom) {
        Worker worker = workerRepository.findById(workerId)
                .orElseThrow(() -> new RuntimeException("Worker not found"));
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        if (room.isFull()) {
            throw new RuntimeException("Room is already full");
        }

        // Vérifie si le worker est déjà membre
        boolean alreadyMember = roomMemberRepository.existsByRoomAndWorker(room, worker);
        if (alreadyMember) {
            throw new RuntimeException("Worker already joined this room");
        }

        RoomMember member = new RoomMember();
        member.setWorker(worker);
        member.setRoom(room);
        member.setRoleInRoom(roleInRoom);
        member.setLead(room.getMembers().isEmpty()); // Premier membre = lead

        RoomMember savedMember = roomMemberRepository.save(member);

        // Vérifie si le room devient plein
        if (roomMemberRepository.findByRoomAndWorker(room, worker).isPresent()) {
            int memberCount = room.getMembers().size();
            if (memberCount >= room.getMaxMembers()) {
                room.setFull(true);
                roomRepository.save(room);
            }
        }

        return roomMemberMapper.toReadDto(savedMember);
    }

    /**
     * 🚪 Un worker quitte un Room.
     */
    @Override
    public void leaveRoom(Long workerId, Long roomId) {
        Worker worker = workerRepository.findById(workerId)
                .orElseThrow(() -> new RuntimeException("Worker not found"));
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));

        RoomMember member = roomMemberRepository.findByRoomAndWorker(room, worker)
                .orElseThrow(() -> new RuntimeException("Worker not in this room"));

        roomMemberRepository.delete(member);

        long remainingMembers = roomMemberRepository.countByRoom(room);
        room.setFull(remainingMembers >= room.getMaxMembers());
        roomRepository.save(room);
    }

    /**
     * 🧾 Lister tous les projets auxquels le worker participe.
     */
    @Override
    public List<ProjectReadDto> getProjectsForWorker(Long workerId) {
        return roomMemberRepository.findByWorker_Id(workerId)
                .stream()
                .map(member -> member.getRoom().getProject())
                .distinct()
                .map(projectMapper::toReadDto)
                .collect(Collectors.toList());
    }

    /**
     * 🔎 Rechercher par domaine.
     */
    @Override
    public List<WorkerReadDto> searchByDomaine(String domaine) {
        return workerRepository.findByDomaineContainingIgnoreCase(domaine)
                .stream()
                .map(workerMapper::toReadDto)
                .collect(Collectors.toList());
    }

    /**
     * 🔎 Rechercher par spécialité.
     */
    @Override
    public List<WorkerReadDto> searchBySpecialite(String specialite) {
        return workerRepository.findBySpecialiteContainingIgnoreCase(specialite)
                .stream()
                .map(workerMapper::toReadDto)
                .collect(Collectors.toList());
    }

    /**
     * 🛠️ Mise à jour du profil du worker.
     */
    @Override
    public WorkerReadDto updateWorker(Long id, WorkerCreateUpdateDto dto) {
        Worker existingWorker = workerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Worker not found"));

        workerMapper.partialUpdate(dto, existingWorker);
        workerRepository.save(existingWorker);

        return workerMapper.toReadDto(existingWorker);
    }

    /**
     * 🗑️ Supprimer un worker.
     */
    @Override
    public void deleteWorker(Long id) {
        if (!workerRepository.existsById(id)) {
            throw new RuntimeException("Worker not found");
        }
        workerRepository.deleteById(id);
    }
}
