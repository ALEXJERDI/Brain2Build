package com.example.brain2build.service.worker;

import com.example.brain2build.domain.dto.Project.ProjectReadDto;
import com.example.brain2build.domain.dto.Worker.WorkerCreateUpdateDto;
import com.example.brain2build.domain.dto.Worker.WorkerReadDto;
import com.example.brain2build.domain.dto.RoomMember.RoomMemberReadDto;

import java.util.List;

public interface WorkerService {

    /**
     * Récupérer un worker par son ID.
     */
    WorkerReadDto getWorkerById(Long id);

    /**
     * Lister tous les workers.
     */
    List<WorkerReadDto> getAllWorkers();

    /**
     * Rejoindre un room existant.
     */
    RoomMemberReadDto joinRoom(Long workerId, Long roomId, String roleInRoom);

    /**
     * Quitter un room.
     */
    void leaveRoom(Long workerId, Long roomId);

    /**
     * Récupérer tous les projets auxquels le worker participe.
     */
    List<ProjectReadDto> getProjectsForWorker(Long workerId);

    /**
     * Rechercher par domaine (ex: Backend, Frontend...).
     */
    List<WorkerReadDto> searchByDomaine(String domaine);

    /**
     * Rechercher par spécialité (ex: Java, React...).
     */
    List<WorkerReadDto> searchBySpecialite(String specialite);

    /**
     * Mettre à jour le profil du worker.
     */
    WorkerReadDto updateWorker(Long id, WorkerCreateUpdateDto dto);

    /**
     * Supprimer un worker.
     */
    void deleteWorker(Long id);
}
