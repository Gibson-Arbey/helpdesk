package co.rabbitmq.publisher;

import co.model.ticketevent.TicketCreatedEvent;
import co.model.ticketevent.gateways.TicketEventRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TicketEventAdapter implements TicketEventRepository {

    private final RabbitTemplate rabbitTemplate;

    @Override
    public void publishTicketCreatedEvent(TicketCreatedEvent event) {
        rabbitTemplate.convertAndSend("ticket-events", "ticket.created", event);
    }
}
