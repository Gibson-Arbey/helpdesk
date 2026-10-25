package co.usecase.ticket;

import co.model.agentprofile.AgentProfile;
import co.model.agentprofile.exception.NoAvailableAgentException;
import co.model.agentprofile.gateways.AgentProfileRepository;
import co.model.ticket.Ticket;
import co.model.ticket.TicketStatus;
import co.model.ticket.exception.TicketNotFoundException;
import co.model.ticket.gateways.TicketRepository;
import co.model.ticketevent.TicketAssignedEvent;
import co.model.ticketevent.gateways.TicketEventRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AssignTicketUseCase {

    private final TicketRepository ticketRepository;
    private final AgentProfileRepository agentProfileRepository;
    private final TicketEventRepository ticketEventRepository;

    public Ticket execute(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId);
        if (ticket == null) {
            throw new TicketNotFoundException("Ticket not found with id: " + ticketId);
        }

        // RabbitMQ can redeliver a creation event after the assignment was committed.
        if (ticket.getStatus() != TicketStatus.CREATED) {
            return ticket;
        }

        AgentProfile availableAgent = agentProfileRepository.findFirstAvailable();
        if (availableAgent == null) {
            throw new NoAvailableAgentException("No available agents found");
        }

        AgentProfile busyAgent = availableAgent.markBusy();
        Ticket assignedTicket = ticket.assignAgent(availableAgent.getUserId());
        var result = ticketRepository.saveTransition(assignedTicket, TicketStatus.CREATED, busyAgent);
        if (result.changed()) {
            ticketEventRepository.publishTicketAssignedEvent(
                    new TicketAssignedEvent(result.ticket().getId(), availableAgent.getUserId())
            );
        }
        return result.ticket();
    }
}
