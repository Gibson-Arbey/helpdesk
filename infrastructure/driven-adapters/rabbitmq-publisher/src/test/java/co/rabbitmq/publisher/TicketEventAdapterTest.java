package co.rabbitmq.publisher;

import co.model.ticketevent.TicketCreatedEvent;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class TicketEventAdapterTest {

    @Test
    void publishesCreatedTicketWithItsRoutingKey() {
        RabbitTemplate rabbitTemplate = mock(RabbitTemplate.class);
        TicketEventAdapter adapter = new TicketEventAdapter(rabbitTemplate);
        TicketCreatedEvent event = new TicketCreatedEvent(25L);

        adapter.publishTicketCreatedEvent(event);

        verify(rabbitTemplate).convertAndSend("ticket-events", "ticket.created", event);
    }
}
