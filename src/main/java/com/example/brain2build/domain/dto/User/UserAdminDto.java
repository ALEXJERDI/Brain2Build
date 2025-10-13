package com.example.brain2build.domain.dto.User;

import lombok.Value;
import com.example.brain2build.domain.dto.Role.RoleDto;
import java.io.Serializable;
import java.time.Instant;
import java.util.Set;

/**
 * DTO for {@link com.example.brain2build.domain.entity.User}
 */
@Value
public class UserAdminDto implements Serializable {
    Long id;
    String email;
    String nom;
    String prenom;
    String telephone;
    Instant createdAt;
    Instant updatedAt;
    Set<RoleDto> roles;
}