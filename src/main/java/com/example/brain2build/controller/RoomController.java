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

    // ------------------------------------------------------------
    // ✅ CREATE ROOM
    // ------------------------------------------------------------
    @PostMapping
    public ResponseEntity<RoomReadDto> createRoom(@Valid @RequestBody RoomCreateDto dto) {
        RoomReadDto createdRoom = roomService.createRoom(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRoom);
    }

    // ------------------------------------------------------------
    // ✅ GET ALL ROOMS
    // ------------------------------------------------------------
    @GetMapping
    public ResponseEntity<List<RoomReadDto>> getAllRooms() {
        List<RoomReadDto> rooms = roomService.getAllRooms();
        return ResponseEntity.ok(rooms);
    }

    // ------------------------------------------------------------
    // ✅ GET ROOM BY ID
    // ------------------------------------------------------------
    @GetMapping("/{id}")
    public ResponseEntity<RoomReadDto> getRoomById(@PathVariable Long id) {
        RoomReadDto room = roomService.getRoomById(id);
        return ResponseEntity.ok(room);
    }

    // ------------------------------------------------------------
    // ✅ UPDATE ROOM (Partial or full)
    // ------------------------------------------------------------
    @PutMapping("/{id}")
    public ResponseEntity<RoomReadDto> updateRoom(
            @PathVariable Long id,
            @Valid @RequestBody RoomUpdateDto dto
    ) {
        RoomReadDto updatedRoom = roomService.updateRoom(id, dto);
        return ResponseEntity.ok(updatedRoom);
    }

    // ------------------------------------------------------------
    // ✅ DELETE ROOM
    // ------------------------------------------------------------
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
        return ResponseEntity.noContent().build();
    }

    // ------------------------------------------------------------
    // ✅ GET MEMBERS IN ROOM
    // ------------------------------------------------------------
    @GetMapping("/{id}/members")
    public ResponseEntity<List<RoomMemberReadDto>> getMembersInRoom(@PathVariable Long id) {
        List<RoomMemberReadDto> members = roomService.getMembersInRoom(id);
        return ResponseEntity.ok(members);
    }

    // ------------------------------------------------------------
    // ✅ GET ROOM BY PROJECT ID
    // ------------------------------------------------------------
    @GetMapping("/project/{projectId}")
    public ResponseEntity<RoomReadDto> getRoomByProjectId(@PathVariable Long projectId) {
        RoomReadDto room = roomService.getRoomByProjectId(projectId);
        return ResponseEntity.ok(room);
    }
}
