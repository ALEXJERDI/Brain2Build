package com.example.brain2build.domain.dto.Ideator;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;
import java.util.Set;

/**
 * DTO for {@link com.example.brain2build.domain.entity.Ideator}
 */
@Value
public class IdeatorCreateUpdateDto implements Serializable {
    @Email(message = "Format d'email invalide")
    @NotBlank(message = "L'email est obligatoire")
    String email;
    @Size(message = "Le mot de passe doit contenir entre 6 et 20 caractères", min = 6, max = 20)
    @NotBlank(message = "Le mot de passe est obligatoire")
    String password;
    @Size(max = 50)
    @NotBlank(message = "Le nom est obligatoire")
    String nom;
    @Size(max = 50)
    @NotBlank(message = "Le prenom est obligatoire")
    String prenom;
    @Pattern(message = "Le numéro de téléphone est invalide", regexp = "^[0-9 +()-]{6,20}$")
    String telephone;
    @Size(message = "La biographie ne peut pas dépasser 500 caractères", max = 500)
    String bio;
}