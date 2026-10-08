package co.model.ticket.gateways;

import co.model.ticket.Ticket;

public interface TicketRepository {

    Ticket save(Ticket ticket);

    Ticket findById(Long id);

}
