package com.example.brain2build.domain.dto.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;
import java.util.Set;

/**
 * DTO for {@link com.example.brain2build.domain.entity.User}
 */
@Value
public class UserCreateUpdateDto implements Serializable {
    @Email(message = "Format d'email invalide")
    @NotBlank(message = "L'email est obligatoire")
    String email;
    @Size(message = "Le mot de passe doit comporter entre 6 et 20 caractères", min = 6, max = 20)
    @NotBlank(message = "Le mot de passe est obligatoire")
    String password;
    @Size(message = "Le nom ne doit pas dépasser 50 caractères", max = 50)
    @NotBlank(message = "Le nom est obligatoire")
    String nom;
    @Size(message = "Le prenom ne doit pas dépasser 50 caractères", max = 50)
    @NotBlank(message = "Le prenom est obligatoire")
    String prenom;
    @Pattern(message = "Le numéro de téléphone doit être valide ", regexp = "^[0-9 +()-]{6,20}$ ")
    String telephone;
    Set<Long> roleIds;
}