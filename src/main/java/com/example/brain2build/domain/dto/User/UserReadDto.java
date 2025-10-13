package com.example.brain2build.domain.dto.User;

import com.example.brain2build.domain.dto.Role.RoleDto;
import lombok.Value;

import java.io.Serializable;
import java.time.Instant;
import java.util.Set;

/**
 * DTO for {@link com.example.brain2build.domain.entity.User}
 */
@Value
public class UserReadDto implements Serializable {
    Long id;
    String email;
    String nom;
    String prenom;
    String telephone;
    Set<RoleDto> roles;
}