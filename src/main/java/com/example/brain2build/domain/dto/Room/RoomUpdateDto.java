package com.example.brain2build.domain.dto.Room;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Value;


@Value
public class RoomUpdateDto {
    @Size(message = "Le nom ne doit pas dépasser 100 caractères", max = 100)
    String roomName;
    @Positive
    int maxMembers;
    Long projectId;
}
