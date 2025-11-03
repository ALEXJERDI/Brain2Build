package com.example.brain2build.controller;



import com.example.brain2build.domain.dto.Room.*;
import com.example.brain2build.domain.dto.RoomMember.RoomMemberReadDto;
import com.example.brain2build.service.RoomService.RoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ✅ Controller REST pour la gestion des Rooms
 * --------------------------------------------
 * Ce controller expose toutes les routes HTTP nécessaires pour :
 *  - créer, lire, mettre à jour et supprimer une Room
 *  - récupérer les membres d’une Room
 *  - récupérer la Room associée à un Project
 */

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    /**
     * ✅ Créer une nouvelle Room
     * Ex: POST /api/rooms
     */
    @PostMapping
    public ResponseEntity<RoomReadDto> createRoom(@Valid @RequestBody RoomCreateUpdateDto dto) {
        RoomReadDto createdRoom = roomService.createRoom(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRoom);
    }

    /**
     * ✅ Récupérer toutes les Rooms
     * Ex: GET /api/rooms
     */
    @GetMapping
    public ResponseEntity<List<RoomReadDto>> getAllRooms() {
        List<RoomReadDto> rooms = roomService.getAllRooms();
        return ResponseEntity.ok(rooms);
    }

    /**
     * ✅ Récupérer une Room par son ID
     * Ex: GET /api/rooms/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<RoomReadDto> getRoomById(@PathVariable Long id) {
        RoomReadDto room = roomService.getRoomById(id);
        return ResponseEntity.ok(room);
    }

    /**
     * ✅ Mettre à jour une Room existante
     * Ex: PUT /api/rooms/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<RoomReadDto> updateRoom(
            @PathVariable Long id,
            @Valid @RequestBody RoomCreateUpdateDto dto
    ) {
        RoomReadDto updatedRoom = roomService.updateRoom(id, dto);
        return ResponseEntity.ok(updatedRoom);
    }

    /**
     * ✅ Supprimer une Room
     * Ex: DELETE /api/rooms/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * ✅ Récupérer tous les membres d'une Room
     * Ex: GET /api/rooms/{id}/members
     */
    @GetMapping("/{id}/members")
    public ResponseEntity<List<RoomMemberReadDto>> getMembersInRoom(@PathVariable Long id) {
        List<RoomMemberReadDto> members = roomService.getMembersInRoom(id);
        return ResponseEntity.ok(members);
    }

    /**
     * ✅ Récupérer la Room associée à un Project
     * Ex: GET /api/rooms/project/{projectId}
     */
    @GetMapping("/project/{projectId}")
    public ResponseEntity<RoomReadDto> getRoomByProjectId(@PathVariable Long projectId) {
        RoomReadDto room = roomService.getRoomByProjectId(projectId);
        return ResponseEntity.ok(room);
    }
}
