package co.usecase.ticket;

import co.model.ticket.Ticket;
import co.model.ticket.exception.AgentNotAssignedToTicketException;
import co.model.ticket.gateways.TicketRepository;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class CloseTicketUseCase {

    private final TicketRepository ticketRepository;

    public void execute(Long ticketId, UUID customerId) {
        Ticket ticket = ticketRepository.findById(ticketId);

        if (ticket == null) {
            throw new RuntimeException("Ticket not found with id: " + ticketId);
        }

        if (!ticket.getCustomerId().equals(customerId)) {
            throw new AgentNotAssignedToTicketException("Customer with id: " + customerId + " is not assigned to ticket with id: " + ticketId);
        }

        Ticket closedTicket = ticket.close();
        ticketRepository.save(closedTicket);
    }
}
