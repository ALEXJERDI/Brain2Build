package org.example.projectservice.messaging;

import lombok.RequiredArgsConstructor;
import org.example.projectservice.config.RabbitMQConfig;
import org.example.contracts.events.ProjectCreatedEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public void publishProjectCreated(ProjectCreatedEvent event) {
        rabbitTemplate.convertAndSend(
                RabbitMQConfig.PROJECT_CREATED_QUEUE,
                event
        );
    }
}
