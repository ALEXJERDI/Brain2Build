package com.example.brain2build.repository;

import com.example.brain2build.domain.entity.RoomMember;
import com.example.brain2build.domain.entity.Room;
import com.example.brain2build.domain.entity.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoomMemberRepository extends JpaRepository<RoomMember, Long> {

    // 🔍 Trouver tous les membres d'une room
    List<RoomMember> findByRoom(Room room);

    // 🔍 Trouver tous les rooms d’un worker
    List<RoomMember> findByWorker(Worker worker);

    // 🔍 Trouver un membre spécifique (worker + room)
    Optional<RoomMember> findByWorkerAndRoom(Worker worker, Room room);

    // 🔍 Lister les leaders d'une room
    List<RoomMember> findByRoomAndIsLeadTrue(Room room);
}

