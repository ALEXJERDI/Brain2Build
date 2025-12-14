package org.example.ideaservice.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String IDEA_APPROVED_QUEUE = "idea.approved.queue";
    public static final String REQUEST_ACTIVATED_QUEUE = "request.activated.queue";

    @Bean
    public Queue ideaApprovedQueue() {
        return new Queue(IDEA_APPROVED_QUEUE, true);
    }

    @Bean
    public Queue requestActivatedQueue() {
        return new Queue(REQUEST_ACTIVATED_QUEUE, true);
    }
    // 🔥 OBLIGATOIRE POUR ENVOYER DES JSON EVENTS À PROJECT-SERVICE
    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
