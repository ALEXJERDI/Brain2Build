package org.example.roomservice.repositorys;

import org.example.roomservice.entity.Room;
import org.example.roomservice.entity.RoomMember;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoomMemberRepository extends JpaRepository<RoomMember, Long> {

    List<RoomMember> findByRoom_Id(Long roomId);

    List<RoomMember> findByWorkerId(Long workerId);

    boolean existsByRoomAndWorkerId(Room room, Long workerId);

    boolean existsByRoom_IdAndLeadTrue(Long roomId);

    long countByRoom_Id(Long roomId);
}

