package org.example.projectservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

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
