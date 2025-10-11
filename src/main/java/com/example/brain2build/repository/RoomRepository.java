package com.example.brain2build.repository;

import com.example.brain2build.domain.entity.Room;
import com.example.brain2build.domain.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    // 🔍 Trouver une room par son nom
    Optional<Room> findByRoomName(String roomName);

    // 🔍 Trouver une room liée à un projet précis
    Optional<Room> findByProject(Project project);

    // 🔍 Lister toutes les rooms pleines
    List<Room> findByIsFullTrue();

    // 🔍 Lister toutes les rooms disponibles
    List<Room> findByIsFullFalse();
}

