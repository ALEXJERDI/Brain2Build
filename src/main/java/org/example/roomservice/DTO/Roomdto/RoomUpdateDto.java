package org.example.roomservice.DTO.Roomdto;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;

@Value
public class RoomUpdateDto implements Serializable {

    @Size(max = 100, message = "Le nom ne doit pas dépasser 100 caractères")
    String roomName;

    @Positive(message = "Le nombre maximum de membres doit être positif")
    Integer maxMembers;

    Long projectId; // facultatif : utilisé seulement si on veut changer la room vers un autre project
}
