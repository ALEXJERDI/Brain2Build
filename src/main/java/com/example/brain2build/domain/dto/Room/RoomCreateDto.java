package com.example.brain2build.domain.dto.Room;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.example.brain2build.domain.entity.Room}
 */
@Value
public class RoomCreateDto implements Serializable {
    @Size(message = "Le nom ne doit pas dépasser 100 caractères", max = 100)
    @NotBlank(message = "Le nom de la room est obligatoire")
    String roomName;
    @Positive
    int maxMembers;
    @NotNull
    Long projectId;

}