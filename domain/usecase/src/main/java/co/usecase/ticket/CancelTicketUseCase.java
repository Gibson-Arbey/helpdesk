package co.usecase.ticket;

import co.model.agentprofile.AgentProfile;
import co.model.agentprofile.exception.AgentNotFoundException;
import co.model.agentprofile.gateways.AgentProfileRepository;
import co.model.ticket.Ticket;
import co.model.ticket.TicketStatus;
import co.model.ticket.exception.TicketNotFoundException;
import co.model.ticket.gateways.TicketRepository;
import co.model.ticketevent.TicketCancelledEvent;
import co.model.ticketevent.gateways.TicketEventRepository;
import co.model.user.exception.CustomerNotOwnerException;
import lombok.RequiredArgsConstructor;

import java.util.Objects;
import java.util.UUID;

@RequiredArgsConstructor
public class CancelTicketUseCase {

    private final TicketRepository ticketRepository;
    private final AgentProfileRepository agentProfileRepository;
    private final TicketEventRepository ticketEventRepository;

    public void execute(Long ticketId, UUID customerId) {
        Ticket ticket = ticketRepository.findById(ticketId);
        if (ticket == null) {
            throw new TicketNotFoundException("Ticket not found with id: " + ticketId);
        }
        if (!Objects.equals(ticket.getCustomerId(), customerId)) {
            throw new CustomerNotOwnerException(
                    "Customer with id: " + customerId + " does not own ticket with id: " + ticketId
            );
        }
        if (ticket.getStatus() == TicketStatus.CANCELLED) {
            return;
        }

        Ticket cancelledTicket = ticket.cancel();
        AgentProfile agentProfile = null;
        if (ticket.getAgentId() != null) {
            agentProfile = agentProfileRepository.findByUserId(ticket.getAgentId());
            if (agentProfile == null) {
                throw new AgentNotFoundException("Assigned agent profile not found with user id: " + ticket.getAgentId());
            }
        }

        var result = ticketRepository.saveTransition(
                cancelledTicket,
                ticket.getStatus(),
                agentProfile == null ? null : agentProfile.releaseCancelledTicket()
        );
        if (result.changed()) {
            ticketEventRepository.publishTicketCancelledEvent(new TicketCancelledEvent(ticketId));
        }
    }
}
