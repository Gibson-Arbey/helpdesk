package co.usecase.ticket;

import co.model.agent.Agent;
import co.model.agentprofile.AgentStatus;
import co.model.agentprofile.exception.NoAvailableAgentException;
import co.model.agent.gateways.AgentRepository;
import co.model.agentprofile.gateways.AgentProfileRepository;
import co.model.ticket.Ticket;
import co.model.ticket.TicketStatus;
import co.model.ticket.exception.InvalidTicketStatusException;
import co.model.ticket.exception.TicketNotFoundException;
import co.model.ticket.gateways.TicketRepository;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class AssignTicketUseCase {

    public final TicketRepository ticketRepository;
    private final AgentProfileRepository agentProfileRepository;

    public void execute(Long ticketId) {

        Ticket ticket = ticketRepository.findById(ticketId);

        if (ticket == null) {
            throw new TicketNotFoundException("Ticket not found with id: " + ticketId);
        }

        if (ticket.getStatus() != null && !ticket.getStatus().equals(TicketStatus.CREATED)) {
            throw new InvalidTicketStatusException("Ticket with id: " + ticketId + " is not in CREATED status and cannot be assigned.");
        }

        UUID agentId = this.findAvailableAgent();

        Ticket assignedTicket = ticket.assignAgent(agentId);

        ticketRepository.save(assignedTicket);

    }

    private UUID findAvailableAgent() {
        UUID agentId = agentProfileRepository.findIdAgentProfileAvaliable();

        if (agentId == null) {
            throw new NoAvailableAgentException("No available agents found.");
        }

        return agentId;
    }
}
