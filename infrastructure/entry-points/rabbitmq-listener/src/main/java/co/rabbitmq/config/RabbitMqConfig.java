package co.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    @Bean
    public MessageConverter messageConverter(){
        return new JacksonJsonMessageConverter();
    }

    @Bean
    public TopicExchange ticketEventsExchange() {
        return new TopicExchange("ticket-events");
    }

    @Bean
    public Queue ticketAssignmentQueue() {
        return new Queue("ticket-assignment-queue", true);
    }

    @Bean
    public Queue ticketNotificationQueue() {
        return new Queue("ticket-notification-queue", true);
    }

    @Bean
    public Queue ticketAuditQueue() {
        return new Queue("ticket-audit-queue", true);
    }

    @Bean
    public Binding assignmentBinding(
            Queue ticketAssignmentQueue,
            TopicExchange ticketEventsExchange) {
        return BindingBuilder.bind(ticketAssignmentQueue)
                .to(ticketEventsExchange)
                .with("ticket.created");
    }

    @Bean
    public Binding notificationBinding(
            Queue ticketNotificationQueue,
            TopicExchange ticketEventsExchange) {
        return BindingBuilder.bind(ticketNotificationQueue)
                .to(ticketEventsExchange)
                .with("ticket.created");
    }

    @Bean
    public Binding auditBinding(
            Queue ticketAuditQueue,
            TopicExchange ticketEventsExchange) {
        return BindingBuilder.bind(ticketAuditQueue)
                .to(ticketEventsExchange)
                .with("ticket.created");
    }
}
