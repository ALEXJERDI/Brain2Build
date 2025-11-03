package com.example.brain2build.domain.dto.Role;

import lombok.AllArgsConstructor;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.example.brain2build.domain.entity.Role}
 */
@Value
public class RoleDto implements Serializable {
    Long id;
    String nom;
}
