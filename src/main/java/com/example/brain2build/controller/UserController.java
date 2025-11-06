package com.example.brain2build.controller;

import com.example.brain2build.domain.dto.User.UserCreateUpdateDto;
import com.example.brain2build.domain.dto.User.UserReadDto;
import com.example.brain2build.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.util.Set;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /** ✅ GET – list all users */
    @GetMapping
    public ResponseEntity<Set<UserReadDto>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    /** ✅ PUT – update an existing user */
    @PutMapping("/{id}")
    @PreAuthorize("#id == authentication.principal.id or hasRole('ADMIN')")
    public ResponseEntity<UserReadDto> updateUser(
            @PathVariable Long id,
            @RequestBody UserCreateUpdateDto dto) {
        return ResponseEntity.ok(userService.updateUser(id, dto));
    }

    /** ✅ DELETE – remove a user */
    @DeleteMapping("/{id}")
    @PreAuthorize("#id == authentication.principal.id or hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
