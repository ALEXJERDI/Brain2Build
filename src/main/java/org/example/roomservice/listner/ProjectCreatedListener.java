package org.example.roomservice.listner;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.roomservice.config.RabbitMQConfig;
import org.example.contracts.events.ProjectCreatedEvent;
import org.example.roomservice.DTO.Roomdto.RoomCreateDto;
import org.example.roomservice.Services.RoomServices.RoomService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProjectCreatedListener {

    private final RoomService roomService;

    @RabbitListener(queues = RabbitMQConfig.PROJECT_CREATED_QUEUE)
    public void handleProjectCreated(ProjectCreatedEvent event) {

        log.info("📩 Received ProjectCreatedEvent: {}", event);

        RoomCreateDto dto = new RoomCreateDto();
        dto.setProjectId(event.getProjectId());
        dto.setRoomName(event.getProjectName());
        dto.setMaxMembers(10);


        roomService.createRoom(dto);

        log.info("✅ Room created for project {}", event.getProjectId());
    }
}
