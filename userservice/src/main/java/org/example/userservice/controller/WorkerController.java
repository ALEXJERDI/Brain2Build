package org.example.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.userservice.domain.dto.WorkerProfile.WorkerProfileReadDto;
import org.example.userservice.domain.dto.WorkerProfile.WorkerProfileUpdateDto;
import org.example.userservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController


@RequestMapping("/workers")
@RequiredArgsConstructor
public class WorkerController {

    private final UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<WorkerProfileReadDto> getWorker(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.getWorkerProfile(userId));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<WorkerProfileReadDto> updateWorker(
            @PathVariable Long userId,
            @RequestBody WorkerProfileUpdateDto dto
    ) {
        return ResponseEntity.ok(userService.updateWorkerProfile(userId, dto));
    }
}

