package com.example.brain2build.service.RoomMemberService;



import com.example.brain2build.domain.dto.RoomMember.*;
import com.example.brain2build.domain.entity.*;
import com.example.brain2build.mappers.RoomMemberMapper;
import com.example.brain2build.repository.*;
import com.example.brain2build.service.RoomMemberService.RoomMemberServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // ➤ Active Mockito pour JUnit 5
class RoomMemberServiceImplTest {

    // 🧱 Mocks des dépendances du service
    @Mock private RoomMemberRepository roomMemberRepository;
    @Mock private RoomRepository roomRepository;
    @Mock private WorkerRepository workerRepository;
    @Mock private RoomMemberMapper roomMemberMapper;

    // 🧠 Injecte les mocks dans une instance réelle de RoomMemberServiceImpl
    @InjectMocks private RoomMemberServiceImpl service;

    // 🧰 Données utilisées dans les tests
    private Room room;
    private Worker worker;
    private RoomMember member;
    private RoomMemberCreateUpdateDto dto;

    @BeforeEach
    void setup() {
        room = new Room();
        room.setId(1L);
        room.setRoomName("Test Room");
        room.setMaxMembers(3);
        room.setMembers(new ArrayList<>());

        worker = new Worker();
        worker.setId(2L);

        member = new RoomMember();
        member.setId(10L);
        member.setRoom(room);
        member.setWorker(worker);
        member.setLead(false);

        dto = new RoomMemberCreateUpdateDto("Developer", false, 2L, 1L);
    }

    // 🧪 Test : ajout d’un worker à une room
    @Test
    void addWorkerToRoom_success() {
        // GIVEN : comportements attendus des mocks
        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));
        when(workerRepository.findById(2L)).thenReturn(Optional.of(worker));
        when(roomMemberRepository.existsByRoomAndWorker(room, worker)).thenReturn(false);
        when(roomMemberMapper.toEntity(dto)).thenReturn(member);
        when(roomMemberRepository.save(any(RoomMember.class))).thenReturn(member);
        when(roomMemberMapper.toReadDto(member))
                .thenReturn(new RoomMemberReadDto(10L, "Developer", false, LocalDateTime.now(), null));

        // WHEN : appel réel du service
        RoomMemberReadDto result = service.addWorkerToRoom(dto);

        // THEN : vérifications
        assertNotNull(result);
        assertEquals(10L, result.getId());
        verify(roomMemberRepository, times(1)).save(any(RoomMember.class));
    }

    // 🧪 Test : erreur si la room est pleine
    @Test
    void addWorkerToRoom_shouldThrowWhenRoomFull() {
        room.setFull(true);
        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));
        when(workerRepository.findById(2L)).thenReturn(Optional.of(worker));

        RuntimeException ex = assertThrows(RuntimeException.class, () -> service.addWorkerToRoom(dto));
        assertEquals("Room is already full", ex.getMessage());
    }

    // 🧪 Test : suppression d’un membre
    @Test
    void removeWorkerFromRoom_success() {
        room.setFull(true);
        when(roomMemberRepository.findById(10L)).thenReturn(Optional.of(member));

        service.removeWorkerFromRoom(10L);

        verify(roomMemberRepository).delete(member);
        verify(roomRepository).save(room);
        assertFalse(room.isFull());
    }

    // 🧪 Test : promotion en lead
    @Test
    void promoteToLead_success() {
        when(roomMemberRepository.findById(10L)).thenReturn(Optional.of(member));
        when(roomMemberRepository.save(any(RoomMember.class))).thenReturn(member);
        when(roomMemberMapper.toReadDto(member))
                .thenReturn(new RoomMemberReadDto(10L, "Developer", true, LocalDateTime.now(), null));

        RoomMemberReadDto result = service.promoteToLead(10L);

        assertTrue(member.isLead());
        assertEquals(10L, result.getId());
        verify(roomMemberRepository).save(member);
    }
}
