package org.example.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.userservice.domain.dto.User.UserCreateFromAuthDto;
import org.example.userservice.domain.dto.User.UserReadDto;
import org.example.userservice.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/internal/users")
@RequiredArgsConstructor
public class InternalUserController {

    private final UserService userService;

    // 🔵 appelé par authservice.register()
    @PostMapping
    public UserReadDto createUserFromAuth(@RequestBody UserCreateFromAuthDto dto) {
        return userService.createUserFromAuth(dto);
    }

    // 🔵 optionnel : pour login si tu en as besoin
    @GetMapping("/email/{email}")
    public UserReadDto getByEmail(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }
}
