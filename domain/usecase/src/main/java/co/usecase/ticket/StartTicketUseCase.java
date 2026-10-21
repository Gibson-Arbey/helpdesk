package co.usecase.ticket;

import co.model.ticket.Ticket;
import co.model.ticket.exception.AgentNotAssignedToTicketException;
import co.model.ticket.gateways.TicketRepository;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class StartTicketUseCase {

    private final TicketRepository ticketRepository;

    public void execute(Long ticketId, UUID agentId) {
        Ticket ticket = ticketRepository.findById(ticketId);

        if (ticket == null) {
            throw new RuntimeException("Ticket not found with id: " + ticketId);
        }

        if (!ticket.getAgentId().equals(agentId)) {
            throw new AgentNotAssignedToTicketException("Agent with id: " + agentId + " is not assigned to ticket with id: " + ticketId);

        }

        Ticket startedTicket = ticket.start();
        ticketRepository.save(startedTicket);
    }
}
