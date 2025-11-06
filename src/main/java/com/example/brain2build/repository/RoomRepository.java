package com.example.brain2build.repository;

import com.example.brain2build.domain.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    Optional<Room> findByProject_Id(Long projectId);
    boolean existsByProject_Id(Long projectId);

}