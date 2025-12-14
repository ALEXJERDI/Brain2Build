package org.example.projectservice.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // Queues consommées (events venant de idea-service)
    public static final String IDEA_APPROVED_QUEUE = "idea.approved.queue";
    public static final String REQUEST_ACTIVATED_QUEUE = "request.activated.queue";

    // Queue produite (event envoyé à room-service)
    public static final String PROJECT_CREATED_QUEUE = "project.created.queue";

    @Bean
    public Queue ideaApprovedQueue() {
        return new Queue(IDEA_APPROVED_QUEUE, true);
    }

    @Bean
    public Queue requestActivatedQueue() {
        return new Queue(REQUEST_ACTIVATED_QUEUE, true);
    }

    @Bean
    public Queue projectCreatedQueue() {
        return new Queue(PROJECT_CREATED_QUEUE, true);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

}


/*
    public static final String PROJECT_EXCHANGE = "project.exchange";
    public static final String PROJECT_CREATED_QUEUE = "project.created.queue";
    public static final String PROJECT_CREATED_ROUTING_KEY = "project.created";

    @Bean
    public TopicExchange projectExchange() {
        return new TopicExchange(PROJECT_EXCHANGE);
    }

    @Bean
    public Queue projectCreatedQueue() {
        return new Queue(PROJECT_CREATED_QUEUE, true);
    }

    @Bean
    public Binding projectCreatedBinding(Queue projectCreatedQueue,
                                         TopicExchange projectExchange) {
        return BindingBuilder.bind(projectCreatedQueue)
                .to(projectExchange)
                .with(PROJECT_CREATED_ROUTING_KEY);
    }

    @Bean
    public MessageConverter jacksonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                         MessageConverter messageConverter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter);
        return template;
    }
}
*/