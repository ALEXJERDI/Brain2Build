package org.example.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.userservice.domain.dto.IdeatorProfile.IdeatorProfileReadDto;
import org.example.userservice.domain.dto.IdeatorProfile.IdeatorProfileUpdateDto;
import org.example.userservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ideators")
@RequiredArgsConstructor
public class IdeatorController {

    private final UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<IdeatorProfileReadDto> getIdeator(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getIdeatorProfile(userId));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<IdeatorProfileReadDto> updateIdeator(
            @PathVariable Long userId,
            @RequestBody IdeatorProfileUpdateDto dto
    ) {
        return ResponseEntity.ok(userService.updateIdeatorProfile(userId, dto));
    }
}

