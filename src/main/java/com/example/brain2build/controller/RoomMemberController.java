package com.example.brain2build.controller;

import com.example.brain2build.domain.dto.RoomMember.RoomMemberCreateUpdateDto;
import com.example.brain2build.domain.dto.RoomMember.RoomMemberReadDto;
import com.example.brain2build.service.RoomMemberService.RoomMemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * ✅ RoomMemberController (RESTful & immuable)
 * --------------------------------------------
 * - Gère les membres d’une Room spécifique
 * - Toutes les routes sont imbriquées sous /api/rooms/{roomId}/members
 * - Respecte le principe d’immuabilité (aucun setter sur DTO)
 */
@RestController
@RequestMapping("/api/rooms/{roomId}/members")
@RequiredArgsConstructor
public class RoomMemberController {

    private final RoomMemberService roomMemberService;

    /**
     * ✅ Ajouter un Worker à une Room
     * Ex: POST /api/rooms/3/members
     */
    @PostMapping
    public ResponseEntity<RoomMemberReadDto> addWorkerToRoom(
            @PathVariable Long roomId,
            @Valid @RequestBody RoomMemberCreateUpdateDto dto) {

        // On crée un nouveau DTO immuable avec le roomId issu de l’URL
        RoomMemberCreateUpdateDto newDto = new RoomMemberCreateUpdateDto(
                dto.getRoleInRoom(),
                dto.isLead(),
                dto.getWorkerId(),
                roomId
        );

        RoomMemberReadDto created = roomMemberService.addWorkerToRoom(newDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * ✅ Lister tous les membres d’une Room
     * Ex: GET /api/rooms/3/members
     */
    @GetMapping
    public ResponseEntity<List<RoomMemberReadDto>> getMembersByRoom(@PathVariable Long roomId) {
        List<RoomMemberReadDto> members = roomMemberService.getMembersByRoom(roomId);
        return ResponseEntity.ok(members);
    }

    /**
     * ✅ Supprimer un membre d’une Room
     * Ex: DELETE /api/rooms/3/members/{memberId}
     */
    @DeleteMapping("/{memberId}")
    public ResponseEntity<Void> removeWorkerFromRoom(@PathVariable Long memberId) {
        roomMemberService.removeWorkerFromRoom(memberId);
        return ResponseEntity.noContent().build();
    }

    /**
     * ✅ Promouvoir un membre en Lead
     * Ex: PUT /api/rooms/3/members/{memberId}/promote
     */
    @PutMapping("/{memberId}/promote")
    public ResponseEntity<RoomMemberReadDto> promoteToLead(@PathVariable Long memberId) {
        RoomMemberReadDto promoted = roomMemberService.promoteToLead(memberId);
        return ResponseEntity.ok(promoted);
    }
}
