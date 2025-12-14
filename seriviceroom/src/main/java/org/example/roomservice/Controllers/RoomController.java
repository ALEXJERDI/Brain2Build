package org.example.roomservice.Controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.roomservice.DTO.Roomdto.RoomCreateDto;
import org.example.roomservice.DTO.Roomdto.RoomReadDto;
import org.example.roomservice.DTO.Roomdto.RoomUpdateDto;
import org.example.roomservice.Services.RoomServices.RoomService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @PostMapping
    public ResponseEntity<RoomReadDto> createRoom(@Valid @RequestBody RoomCreateDto dto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(roomService.createRoom(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomReadDto> getRoomById(@PathVariable Long id) {
        return ResponseEntity.ok(roomService.getRoomById(id));
    }

    @GetMapping
    public ResponseEntity<List<RoomReadDto>> getAllRooms() {
        return ResponseEntity.ok(roomService.getAllRooms());
    }

    @PutMapping("/{id}")
    public ResponseEntity<RoomReadDto> updateRoom(
            @PathVariable Long id,
            @Valid @RequestBody RoomUpdateDto dto) {
        return ResponseEntity.ok(roomService.updateRoom(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        roomService.deleteRoom(id);
        return ResponseEntity.noContent().build();
    }
}

