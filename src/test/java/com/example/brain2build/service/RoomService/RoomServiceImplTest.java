package com.example.brain2build.service.RoomService;





import com.example.brain2build.domain.dto.Room.RoomCreateUpdateDto;
import com.example.brain2build.domain.dto.Room.RoomReadDto;
import com.example.brain2build.domain.dto.RoomMember.RoomMemberReadDto;
import com.example.brain2build.domain.entity.Project;
import com.example.brain2build.domain.entity.Room;
import com.example.brain2build.domain.entity.RoomMember;
import com.example.brain2build.mappers.RoomMapper;
import com.example.brain2build.mappers.RoomMemberMapper;
import com.example.brain2build.repository.ProjectRepository;
import com.example.brain2build.repository.RoomMemberRepository;
import com.example.brain2build.repository.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * ✅ TEST UNITAIRE DU SERVICE RoomServiceImpl (Mocké avec Mockito)
 *
 * Ce test vérifie :
 *  - La création, lecture, mise à jour et suppression de Room
 *  - La récupération des membres d'une Room
 *  - La recherche d'une Room via un Project
 *
 * 👉 Aucune base de données réelle n’est utilisée.
 * Tout est simulé grâce à Mockito.
 */
class RoomServiceImplTest {

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private RoomMemberRepository roomMemberRepository;

    @Mock
    private RoomMapper roomMapper;

    @Mock
    private RoomMemberMapper roomMemberMapper;

    @InjectMocks
    private RoomServiceImpl roomService;

    private Room room;
    private Project project;
    private RoomReadDto roomReadDto;
    private RoomCreateUpdateDto roomCreateDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // --- Initialisation de base ---
        project = new Project();
        project.setId(1L);
        project.setProjectName("Brain Builder");

        room = new Room();
        room.setId(1L);
        room.setRoomName("AI Room");
        room.setProject(project);
        room.setMaxMembers(5);

        roomReadDto = new RoomReadDto(
                1L,
                "AI Room",
                5,
                false,
                null,
                null,
                List.of()
        );

        roomCreateDto = new RoomCreateUpdateDto("AI Room", 5, 1L);
    }

    // ------------------------------------------------------------
    // ✅ CREATE ROOM
    // ------------------------------------------------------------
    @Test
    @DisplayName("✅ createRoom() doit sauvegarder et retourner une RoomReadDto")
    void createRoom_shouldSaveAndReturnDto() {
        when(roomMapper.toEntity(any(RoomCreateUpdateDto.class))).thenReturn(room);
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(roomRepository.save(any(Room.class))).thenReturn(room);
        when(roomMapper.toReadDto(any(Room.class))).thenReturn(roomReadDto);

        RoomReadDto result = roomService.createRoom(roomCreateDto);

        assertThat(result).isNotNull();
        assertThat(result.getRoomName()).isEqualTo("AI Room");
        verify(roomRepository, times(1)).save(room);
    }

    // ------------------------------------------------------------
    // ✅ GET ROOM BY ID
    // ------------------------------------------------------------
    @Test
    @DisplayName("✅ getRoomById() doit retourner une Room existante")
    void getRoomById_shouldReturnDto() {
        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));
        when(roomMapper.toReadDto(any(Room.class))).thenReturn(roomReadDto);

        RoomReadDto result = roomService.getRoomById(1L);

        assertThat(result).isNotNull();
        assertThat(result.getRoomName()).isEqualTo("AI Room");
        verify(roomRepository).findById(1L);
    }

    // ------------------------------------------------------------
    // ✅ GET ALL ROOMS
    // ------------------------------------------------------------
    @Test
    @DisplayName("✅ getAllRooms() doit retourner toutes les Rooms")
    void getAllRooms_shouldReturnList() {
        when(roomRepository.findAll()).thenReturn(List.of(room));
        when(roomMapper.toReadDto(room)).thenReturn(roomReadDto);

        List<RoomReadDto> result = roomService.getAllRooms();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getRoomName()).isEqualTo("AI Room");
    }

    // ------------------------------------------------------------
    // ✅ UPDATE ROOM
    // ------------------------------------------------------------
    @Test
    @DisplayName("✅ updateRoom() doit modifier et retourner une RoomReadDto")
    void updateRoom_shouldUpdateAndReturnDto() {
        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));
        when(roomRepository.save(any(Room.class))).thenReturn(room);
        when(roomMapper.toReadDto(any(Room.class))).thenReturn(roomReadDto);

        RoomReadDto result = roomService.updateRoom(1L, roomCreateDto);

        assertThat(result).isNotNull();
        verify(roomMapper, times(1)).partialUpdate(any(RoomCreateUpdateDto.class), any(Room.class));
    }

    // ------------------------------------------------------------
    // ✅ DELETE ROOM
    // ------------------------------------------------------------
    @Test
    @DisplayName("✅ deleteRoom() doit supprimer une Room existante")
    void deleteRoom_shouldDeleteExistingRoom() {
        when(roomRepository.existsById(1L)).thenReturn(true);

        roomService.deleteRoom(1L);

        verify(roomRepository, times(1)).deleteById(1L);
    }

    // ------------------------------------------------------------
    // ✅ GET MEMBERS IN ROOM
    // ------------------------------------------------------------
    @Test
    @DisplayName("✅ getMembersInRoom() doit retourner les membres d'une Room")
    void getMembersInRoom_shouldReturnListOfMembers() {
        RoomMember member = new RoomMember();
        member.setId(1L);
        member.setRoom(room);
        member.setRoleInRoom("Designer");

        // DTO immuable → on utilise le constructeur
        RoomMemberReadDto memberDto = new RoomMemberReadDto(
                1L,
                "Designer",
                false,
                null,
                null
        );

        when(roomMemberRepository.findByRoom_Id(1L)).thenReturn(List.of(member));
        when(roomMemberMapper.toReadDto(member)).thenReturn(memberDto);

        List<RoomMemberReadDto> result = roomService.getMembersInRoom(1L);

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getRoleInRoom()).isEqualTo("Designer");
    }

    // ------------------------------------------------------------
    // ✅ GET ROOM BY PROJECT ID
    // ------------------------------------------------------------
    @Test
    @DisplayName("✅ getRoomByProjectId() doit retourner la Room liée à un projet")
    void getRoomByProjectId_shouldReturnRoom() {
        when(roomRepository.findByProject_Id(1L)).thenReturn(Optional.of(room));
        when(roomMapper.toReadDto(room)).thenReturn(roomReadDto);

        RoomReadDto result = roomService.getRoomByProjectId(1L);

        assertThat(result).isNotNull();
        assertThat(result.getRoomName()).isEqualTo("AI Room");
        verify(roomRepository).findByProject_Id(1L);
    }
}
