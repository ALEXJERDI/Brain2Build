package com.example.brain2build.domain.dto.Worker;

import jakarta.validation.constraints.*;
import lombok.Value;
import org.hibernate.validator.constraints.URL;

import java.io.Serializable;
import java.util.Set;

/**
 * DTO for {@link com.example.brain2build.domain.entity.Worker}
 */
@Value
public class WorkerCreateUpdateDto implements Serializable {
    @Email(message = "L'email est obligatoire")
    @NotBlank(message = "Format d'email invalide")
    String email;
    @Size(message = "Le mot de passe doit contenir entre 6 et 20 caractères", min = 6, max = 20)
    @NotBlank(message = "Le mot de passe est obligatoire")
    String password;
    @Size(message = "Le nom ne doit pas dépasser 50 caractères", max = 50)
    @NotBlank(message = "Le nom est obligatoire")
    String nom;
    @Size(message = "Le prenom ne doit pas dépasser 50 caractères", max = 50)
    @NotBlank(message = "Le prenom est obligatoire")
    String prenom;
    @Pattern(message = "Le numéro de téléphone est invalide", regexp = "^[0-9 +()-]{6,20}$")
    String telephone;
    @Size(message = "Le domaine ne doit pas dépasser 100 caractères", max = 100)
    @NotBlank(message = "Le domaine est obligatoire")
    String domaine;
    @Size(message = "La spécialité ne doit pas dépasser 100 caractères", max = 100)
    @NotBlank(message = "La spécialité est obligatoire")
    String specialite;
    @PositiveOrZero(message = "L'expérience doit être positive ou nulle")
    int experience;
    @URL(message = "Le lien du portfolio doit être une URL valide", protocol = "", host = "", regexp = "")
    String portfolioUrl;
}