package org.example.roomservice.Services.RoomServices;

import lombok.RequiredArgsConstructor;
import org.example.contracts.events.ProjectCreatedEvent;
import org.example.roomservice.entity.Room;
import org.example.roomservice.mappers.RoomMapper;
import org.example.roomservice.repositorys.RoomRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoomCreationService {

    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;

    public void createRoomFromProject(ProjectCreatedEvent event) {

        // Empêcher la création d'une 2e room pour le même project
        if (roomRepository.existsByProjectId(event.getProjectId())) {
            throw new IllegalStateException("Room already exists for project: " + event.getProjectId());
        }

        Room room = new Room();
        room.setProjectId(event.getProjectId());
        room.setRoomName("Room - " + event.getProjectName());
        room.setMaxMembers(10);
        room.setFull(false);

        roomRepository.save(room);
    }
}
