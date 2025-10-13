package com.example.brain2build.domain.dto.Admin;

import com.example.brain2build.domain.dto.Role.RoleDto;
import lombok.Value;

import java.io.Serializable;
import java.util.Set;

/**
 * DTO for {@link com.example.brain2build.domain.entity.Admin}
 */
@Value
public class AdminReadDto implements Serializable {
    Long id;
    String email;
    String nom;
    String prenom;
    String telephone;
    Set<RoleDto> roles;
    String privilegeLevel;
    String department;
}