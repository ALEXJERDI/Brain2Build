package org.example.ideaservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.ideaservice.dto.request.RequestCreateDto;
import org.example.ideaservice.dto.request.RequestReadDto;
import org.example.ideaservice.dto.request.RequestUpdateDto;
import org.example.ideaservice.service.RequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/requests")
public class RequestController {

    private final RequestService requestService;

    // --------------------------------------
    // GET ALL REQUESTS
    // --------------------------------------
    @GetMapping
    public ResponseEntity<List<RequestReadDto>> getAllRequests() {
        return ResponseEntity.ok(requestService.getAllRequests());
    }

    // --------------------------------------
    // GET REQUEST BY ID
    // --------------------------------------
    @GetMapping("/{id}")
    public ResponseEntity<RequestReadDto> getRequest(@PathVariable Long id) {
        return ResponseEntity.ok(requestService.getRequest(id));
    }

    // --------------------------------------
    // CREATE REQUEST
    // --------------------------------------
    @PostMapping
    public ResponseEntity<RequestReadDto> createRequest(
            @Valid @RequestBody RequestCreateDto dto
    ) {
        return ResponseEntity.ok(requestService.createRequest(dto));
    }


    // --------------------------------------
    // UPDATE REQUEST (PARTIAL UPDATE)
    // --------------------------------------
    @PutMapping("/{id}")
    public ResponseEntity<RequestReadDto> updateRequest(
            @PathVariable Long id,
            @RequestBody RequestUpdateDto dto
    ) {
        return ResponseEntity.ok(requestService.updateRequest(id, dto));
    }

    // --------------------------------------
    // ACTIVATE REQUEST (becomes active)
    // --------------------------------------
    @PutMapping("/{id}/activate")
    public ResponseEntity<RequestReadDto> activateRequest(@PathVariable Long id) {
        return ResponseEntity.ok(requestService.activateRequest(id));
    }

    // --------------------------------------
    // CLOSE REQUEST
    // --------------------------------------
    @PutMapping("/{id}/close")
    public ResponseEntity<RequestReadDto> closeRequest(@PathVariable Long id) {
        return ResponseEntity.ok(requestService.closeRequest(id));
    }
}
