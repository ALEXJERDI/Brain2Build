package com.example.brain2build.domain.dto.Admin;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;
import java.util.Set;

/**
 * DTO for {@link com.example.brain2build.domain.entity.Admin}
 */
@Value
public class AdminCreateUpdateDto implements Serializable {
    @Email
    @NotBlank
    String email;
    @Size(min = 6, max = 30)
    @NotBlank
    String password;
    @Size(max = 30)
    @NotBlank
    String nom;
    @Size(max = 30)
    @NotBlank
    String prenom;
    @Pattern(regexp = "^[0-9 +()-]{6,20}$")
    @NotBlank
    String telephone;
    Set<Long> roleIds;
    @NotBlank
    String privilegeLevel;
    @NotBlank
    String department;
}