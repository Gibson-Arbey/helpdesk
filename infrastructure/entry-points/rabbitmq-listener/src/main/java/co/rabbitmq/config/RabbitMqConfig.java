package co.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    private static final String EVENTS_EXCHANGE = "ticket-events";
    private static final String DEAD_LETTER_EXCHANGE = "ticket-events.dlx";

    @Bean
    public MessageConverter messageConverter() {
        return new JacksonJsonMessageConverter();
    }

    @Bean
    public TopicExchange ticketEventsExchange() {
        return new TopicExchange(EVENTS_EXCHANGE, true, false);
    }

    @Bean
    public DirectExchange ticketEventsDeadLetterExchange() {
        return new DirectExchange(DEAD_LETTER_EXCHANGE, true, false);
    }

    @Bean
    public Queue ticketAssignmentQueue() {
        return QueueBuilder.durable("ticket-assignment-queue")
                .deadLetterExchange(DEAD_LETTER_EXCHANGE)
                .deadLetterRoutingKey("ticket.assignment.dead")
                .build();
    }

    @Bean
    public Queue ticketNotificationQueue() {
        return QueueBuilder.durable("ticket-notification-queue")
                .deadLetterExchange(DEAD_LETTER_EXCHANGE)
                .deadLetterRoutingKey("ticket.notification.dead")
                .build();
    }

    @Bean
    public Queue ticketAuditQueue() {
        return QueueBuilder.durable("ticket-audit-queue")
                .deadLetterExchange(DEAD_LETTER_EXCHANGE)
                .deadLetterRoutingKey("ticket.audit.dead")
                .build();
    }

    @Bean
    public Queue ticketAssignmentDeadLetterQueue() {
        return QueueBuilder.durable("ticket-assignment-dlq").build();
    }

    @Bean
    public Queue ticketNotificationDeadLetterQueue() {
        return QueueBuilder.durable("ticket-notification-dlq").build();
    }

    @Bean
    public Queue ticketAuditDeadLetterQueue() {
        return QueueBuilder.durable("ticket-audit-dlq").build();
    }

    @Bean
    public Binding assignmentBinding(Queue ticketAssignmentQueue, TopicExchange ticketEventsExchange) {
        return BindingBuilder.bind(ticketAssignmentQueue).to(ticketEventsExchange).with("ticket.created");
    }

    @Bean
    public Binding notificationBinding(Queue ticketNotificationQueue, TopicExchange ticketEventsExchange) {
        return BindingBuilder.bind(ticketNotificationQueue).to(ticketEventsExchange).with("ticket.#");
    }

    @Bean
    public Binding auditBinding(Queue ticketAuditQueue, TopicExchange ticketEventsExchange) {
        return BindingBuilder.bind(ticketAuditQueue).to(ticketEventsExchange).with("ticket.#");
    }

    @Bean
    public Binding assignmentDeadLetterBinding(Queue ticketAssignmentDeadLetterQueue, DirectExchange ticketEventsDeadLetterExchange) {
        return BindingBuilder.bind(ticketAssignmentDeadLetterQueue).to(ticketEventsDeadLetterExchange).with("ticket.assignment.dead");
    }

    @Bean
    public Binding notificationDeadLetterBinding(Queue ticketNotificationDeadLetterQueue, DirectExchange ticketEventsDeadLetterExchange) {
        return BindingBuilder.bind(ticketNotificationDeadLetterQueue).to(ticketEventsDeadLetterExchange).with("ticket.notification.dead");
    }

    @Bean
    public Binding auditDeadLetterBinding(Queue ticketAuditDeadLetterQueue, DirectExchange ticketEventsDeadLetterExchange) {
        return BindingBuilder.bind(ticketAuditDeadLetterQueue).to(ticketEventsDeadLetterExchange).with("ticket.audit.dead");
    }
}
