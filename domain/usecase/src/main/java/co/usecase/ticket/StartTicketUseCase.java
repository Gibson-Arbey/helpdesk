package co.usecase.ticket;

import co.model.ticket.Ticket;
import co.model.ticket.TicketStatus;
import co.model.ticket.exception.AgentNotAssignedToTicketException;
import co.model.ticket.exception.TicketNotFoundException;
import co.model.ticket.gateways.TicketRepository;
import co.model.ticketevent.TicketStartedEvent;
import co.model.ticketevent.gateways.TicketEventRepository;
import lombok.RequiredArgsConstructor;

import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
public class StartTicketUseCase {

    private final TicketRepository ticketRepository;
    private final TicketEventRepository ticketEventRepository;

    public void execute(Long ticketId, UUID agentId) {
        Ticket ticket = ticketRepository.findById(ticketId);
        if (ticket == null) {
            throw new TicketNotFoundException("Ticket not found with id: " + ticketId);
        }
        if (!Objects.equals(ticket.getAgentId(), agentId)) {
            throw new AgentNotAssignedToTicketException(
                    "Agent with id: " + agentId + " is not assigned to ticket with id: " + ticketId
            );
        }
        if (ticket.getStatus() == TicketStatus.IN_PROGRESS) {
            return;
        }

        var result = ticketRepository.saveTransition(ticket.start(), TicketStatus.ASSIGNED, null);
        if (result.changed()) {
            ticketEventRepository.publishTicketStartedEvent(new TicketStartedEvent(ticketId));
        }
    }
}
