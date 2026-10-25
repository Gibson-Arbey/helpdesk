package co.model.ticketevent.gateways;

import co.model.ticketevent.TicketCreatedEvent;
import co.model.ticketevent.TicketAssignedEvent;
import co.model.ticketevent.TicketCancelledEvent;
import co.model.ticketevent.TicketClosedEvent;
import co.model.ticketevent.TicketResolvedEvent;
import co.model.ticketevent.TicketStartedEvent;

public interface TicketEventRepository {

    void publishTicketCreatedEvent(TicketCreatedEvent event);

    void publishTicketAssignedEvent(TicketAssignedEvent event);

    void publishTicketStartedEvent(TicketStartedEvent event);

    void publishTicketResolvedEvent(TicketResolvedEvent event);

    void publishTicketClosedEvent(TicketClosedEvent event);

    void publishTicketCancelledEvent(TicketCancelledEvent event);
}
