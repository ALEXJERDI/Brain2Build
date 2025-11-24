package org.example.roomservice.repositorys;

import org.example.roomservice.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    Optional<Room> findByProjectId(Long projectId);
    boolean existsByProjectId(Long projectId);
    boolean existsByRoomNameIgnoreCase(String roomName);


}

