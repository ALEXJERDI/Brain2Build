package org.example.roomservice.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    // 👇 même nom EXACT que dans projectservice
    public static final String PROJECT_CREATED_QUEUE = "project.created.queue";

    @Bean
    public Queue projectCreatedQueue() {
        return new Queue(PROJECT_CREATED_QUEUE, true);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
