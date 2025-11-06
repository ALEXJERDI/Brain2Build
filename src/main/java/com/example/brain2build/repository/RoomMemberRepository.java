package com.example.brain2build.repository;

import com.example.brain2build.domain.entity.Room;
import com.example.brain2build.domain.entity.RoomMember;
import com.example.brain2build.domain.entity.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface RoomMemberRepository extends JpaRepository<RoomMember, Long> {

    boolean existsByRoomAndWorker(Room room, Worker worker);

    Optional<RoomMember> findByRoomAndWorker(Room room, Worker worker);

    List<RoomMember> findByWorker_Id(Long workerId);
    List<RoomMember> findByRoom_Id(Long roomId);
    long countByRoom(Room room);



}
