package org.example.roomservice.Controllers;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.roomservice.DTO.Roommemberdto.RoomMemberCreateUpdateDto;
import org.example.roomservice.DTO.Roommemberdto.RoomMemberReadDto;
import org.example.roomservice.Services.RoomMemberServices.RoomMemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

        // Ajouter un worker dans la room
        RoomMemberReadDto created = roomMemberService.addWorkerToRoom(newDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /**
     * ✅ Lister tous les membres d’une Room
     * Ex: GET /api/rooms/3/members
     */
    @GetMapping
    public ResponseEntity<List<RoomMemberReadDto>> getMembers(@PathVariable Long roomId) {
        List<RoomMemberReadDto> members = roomMemberService.getMembersByRoom(roomId);
        return ResponseEntity.ok(members);
    }

    /**
     * ✅ Promouvoir un membre en Lead
     * Ex: PUT /api/rooms/3/members/{memberId}/promote
     */
    @PutMapping("/{memberId}/promote")
    public ResponseEntity<RoomMemberReadDto> promoteToLead(@PathVariable Long roomId, @PathVariable Long memberId) {
        // Promouvoir un membre en lead
        RoomMemberReadDto promoted = roomMemberService.promoteToLead(memberId);
        return ResponseEntity.ok(promoted);
    }

    /**
     * ✅ Supprimer un membre d’une Room
     * Ex: DELETE /api/rooms/3/members/{memberId}
     */
    @DeleteMapping("/{memberId}")
    public ResponseEntity<Void> removeWorkerFromRoom(@PathVariable Long roomId, @PathVariable Long memberId) {
        // Supprimer un worker de la room
        roomMemberService.removeWorkerFromRoom(memberId);
        return ResponseEntity.noContent().build();
    }
}

