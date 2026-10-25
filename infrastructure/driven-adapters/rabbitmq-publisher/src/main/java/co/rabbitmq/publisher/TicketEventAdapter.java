package co.rabbitmq.publisher;

import co.model.ticketevent.TicketCreatedEvent;
import co.model.ticketevent.TicketAssignedEvent;
import co.model.ticketevent.TicketCancelledEvent;
import co.model.ticketevent.TicketClosedEvent;
import co.model.ticketevent.TicketResolvedEvent;
import co.model.ticketevent.TicketStartedEvent;
import co.model.ticketevent.gateways.TicketEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TicketEventAdapter implements TicketEventRepository {

    private static final String EXCHANGE = "ticket-events";
    private final RabbitTemplate rabbitTemplate;

    @Override
    public void publishTicketCreatedEvent(TicketCreatedEvent event) {
        rabbitTemplate.convertAndSend(EXCHANGE, "ticket.created", event);
    }

    @Override
    public void publishTicketAssignedEvent(TicketAssignedEvent event) {
        rabbitTemplate.convertAndSend(EXCHANGE, "ticket.assigned", event);
    }

    @Override
    public void publishTicketStartedEvent(TicketStartedEvent event) {
        rabbitTemplate.convertAndSend(EXCHANGE, "ticket.started", event);
    }

    @Override
    public void publishTicketResolvedEvent(TicketResolvedEvent event) {
        rabbitTemplate.convertAndSend(EXCHANGE, "ticket.resolved", event);
    }

    @Override
    public void publishTicketClosedEvent(TicketClosedEvent event) {
        rabbitTemplate.convertAndSend(EXCHANGE, "ticket.closed", event);
    }

    @Override
    public void publishTicketCancelledEvent(TicketCancelledEvent event) {
        rabbitTemplate.convertAndSend(EXCHANGE, "ticket.cancelled", event);
    }
}
