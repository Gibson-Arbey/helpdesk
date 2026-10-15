package co.usecase.ticket;

import co.model.ticket.Ticket;
import co.model.ticket.exception.TicketNotFoundException;
import co.model.ticket.gateways.TicketRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GetTicketByIdUseCase {

    private final TicketRepository ticketRepository;

    public Ticket execute(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId);

        if (ticket == null) {
            throw new TicketNotFoundException("Ticket not found with id: " + ticketId);
        }

        return ticket;
    }
}
