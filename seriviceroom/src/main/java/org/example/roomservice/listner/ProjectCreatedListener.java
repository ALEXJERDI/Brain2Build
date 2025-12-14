package org.example.roomservice.listner;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.contracts.events.ProjectCreatedEvent;
import org.example.roomservice.config.RabbitMQConfig;
import org.example.roomservice.Services.RoomServices.RoomCreationService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProjectCreatedListener {

    private final RoomCreationService roomCreationService;

    @RabbitListener(queues = RabbitMQConfig.PROJECT_CREATED_QUEUE)
    public void handleProjectCreated(ProjectCreatedEvent event) {

        log.info("📩 Received ProjectCreatedEvent: {}", event);

        // Appel normal, pas statique
        roomCreationService.createRoomFromProject(event);

        log.info("🏗️ Room created for project {}", event.getProjectId());
    }
}
