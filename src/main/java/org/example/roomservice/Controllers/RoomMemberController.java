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

    @PostMapping
    public ResponseEntity<RoomMemberReadDto> joinRoom(
            @PathVariable Long roomId,
            @Valid @RequestBody RoomMemberCreateUpdateDto dto) {

        dto.setRoomId(roomId);

        RoomMemberReadDto created = roomMemberService.joinRoom(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<RoomMemberReadDto>> getMembers(@PathVariable Long roomId) {
        return ResponseEntity.ok(roomMemberService.getMembersByRoom(roomId));
    }

    @PatchMapping("/{memberId}/lead")
    public ResponseEntity<RoomMemberReadDto> promoteToLead(@PathVariable Long memberId) {
        return ResponseEntity.ok(roomMemberService.promoteToLead(memberId));
    }

    @DeleteMapping("/{memberId}")
    public ResponseEntity<Void> removeWorker(@PathVariable Long memberId) {
        roomMemberService.removeWorkerFromRoom(memberId);
        return ResponseEntity.noContent().build();
    }
}
