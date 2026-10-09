package co.model.ticketevent.gateways;

import co.model.ticketevent.TicketCreatedEvent;

public interface TicketEventRepository {

    void publishTicketCreatedEvent(TicketCreatedEvent event);
}
