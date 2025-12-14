package org.example.ideaservice.messaging;

import lombok.RequiredArgsConstructor;
import org.example.ideaservice.config.RabbitMQConfig;
import org.example.contracts.events.IdeaApprovedEvent;
import org.example.contracts.events.RequestActivatedEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EventPublisher {

    private final RabbitTemplate rabbitTemplate;

    // ------------------------------
    // Publish Idea Approved event
    // ------------------------------
    public void publishIdeaApproved(IdeaApprovedEvent event) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.IDEA_APPROVED_QUEUE, event);
    }

    // ------------------------------
    // Publish Request Activated event
    // ------------------------------
    public void publishRequestActivated(RequestActivatedEvent event) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.REQUEST_ACTIVATED_QUEUE, event);
    }
}
