package org.example.roomservice.DTO.Roomdto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomCreateDto implements Serializable {

    @Size(message = "Le nom ne doit pas dépasser 100 caractères", max = 100)
    @NotBlank(message = "Le nom de la room est obligatoire")  // validation on DTO
    private String roomName;

    @Positive
    private int maxMembers;

    @NotNull
    private Long projectId;
}

