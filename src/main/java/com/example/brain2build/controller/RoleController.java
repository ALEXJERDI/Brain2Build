package com.example.brain2build.controller;

import com.example.brain2build.domain.dto.Role.RoleDto;
import com.example.brain2build.service.role.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    /** ✅  Create a new role */
    @PostMapping
    public ResponseEntity<RoleDto> createRole(@RequestBody RoleDto dto) {
        return ResponseEntity.ok(roleService.createRole(dto));
    }

    /** ✅  Get all existing roles */
    @GetMapping
    public ResponseEntity<List<RoleDto>> getAllRoles() {
        return ResponseEntity.ok(roleService.getAllRoles());
    }
}
