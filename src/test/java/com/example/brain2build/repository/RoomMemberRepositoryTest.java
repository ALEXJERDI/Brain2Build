package com.example.brain2build.repository;

import com.example.brain2build.domain.entity.Room;
import com.example.brain2build.domain.entity.RoomMember;
import com.example.brain2build.domain.entity.Worker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * ✅ TEST DU ROOMMEMBERREPOSITORY (AVEC POSTGRES LOCAL)
 * ----------------------------------------------------
 * Ce test :
 *  - se connecte à la base PostgreSQL locale `Brain2Build_test`
 *  - crée des entités Room / Worker / RoomMember
 *  - teste les méthodes du repository
 *  - rollback automatiquement la transaction après chaque test
 */

@DataJpaTest
@ActiveProfiles("test") // Pour forcer Spring à utiliser application-test.properties (si tu l’as nommé ainsi)
class RoomMemberRepositoryTest {

    @Autowired
    private RoomMemberRepository roomMemberRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private WorkerRepository workerRepository;

    private Room room;
    private Worker worker;
    private RoomMember member;

    @BeforeEach
    void setup() {
        // --- Création d’une Room ---
        room = new Room();
        room.setRoomName("Design Room");
        room.setMaxMembers(5);
        roomRepository.save(room);

        // --- Création d’un Worker ---
        worker = new Worker();
        worker.setEmail("alice@example.com");
        worker.setPassword("password123");
        worker.setPrenom("Alice");
        worker.setNom("Doe");
        worker.setDomaine("Design");
        worker.setSpecialite("UI/UX");
        worker.setExperience(3);
        workerRepository.save(worker);

        // --- Création d’un RoomMember ---
        member = new RoomMember();
        member.setRoom(room);
        member.setWorker(worker);
        member.setRoleInRoom("Designer");
        member.setLead(false);
        roomMemberRepository.save(member);
    }

    /**
     * ✅ Vérifie que la méthode existsByRoomAndWorker fonctionne.
     */
    @Test
    void existsByRoomAndWorker_shouldReturnTrue() {
        boolean exists = roomMemberRepository.existsByRoomAndWorker(room, worker);
        assertThat(exists).isTrue();
    }

    /**
     * ✅ Vérifie que findByRoomAndWorker renvoie bien le bon RoomMember.
     */
    @Test
    void findByRoomAndWorker_shouldReturnRoomMember() {
        Optional<RoomMember> found = roomMemberRepository.findByRoomAndWorker(room, worker);

        assertThat(found).isPresent();
        assertThat(found.get().getRoleInRoom()).isEqualTo("Designer");
        assertThat(found.get().getWorker().getPrenom()).isEqualTo("Alice");
    }

    /**
     * ✅ Vérifie que findByWorker_Id renvoie bien la liste de RoomMember associés au Worker.
     */
    @Test
    void findByWorkerId_shouldReturnListOfMembers() {
        List<RoomMember> members = roomMemberRepository.findByWorker_Id(worker.getId());

        assertThat(members).hasSize(1);
        assertThat(members.get(0).getRoom().getRoomName()).isEqualTo("Design Room");
    }

    /**
     * ✅ Vérifie que findByRoom_Id renvoie bien la liste des membres d'une Room.
     */
    @Test
    void findByRoomId_shouldReturnListOfMembers() {
        List<RoomMember> members = roomMemberRepository.findByRoom_Id(room.getId());

        assertThat(members).hasSize(1);
        assertThat(members.get(0).getWorker().getPrenom()).isEqualTo("Alice");
    }

    /**
     * ✅ Vérifie que la suppression d’un RoomMember le retire bien de la base.
     */
    @Test
    void deleteRoomMember_shouldRemoveItFromDatabase() {
        roomMemberRepository.delete(member);

        boolean exists = roomMemberRepository.existsByRoomAndWorker(room, worker);
        assertThat(exists).isFalse();
    }
}