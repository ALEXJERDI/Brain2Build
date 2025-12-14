package org.example.projectservice.messaging;

import lombok.RequiredArgsConstructor;
import org.example.projectservice.config.RabbitMQConfig;
import org.example.contracts.events.IdeaApprovedEvent;
import org.example.contracts.events.RequestActivatedEvent;
import org.example.projectservice.service.ProjectCreationService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProjectEventConsumer {

    private final ProjectCreationService projectCreationService;

    @RabbitListener(queues = RabbitMQConfig.IDEA_APPROVED_QUEUE)
    public void handleIdeaApproved(IdeaApprovedEvent event) {
        projectCreationService.createProjectFromIdea(event);
    }

    @RabbitListener(queues = RabbitMQConfig.REQUEST_ACTIVATED_QUEUE)
    public void handleRequestActivated(RequestActivatedEvent event) {
        projectCreationService.createProjectFromRequest(event);
    }
}
