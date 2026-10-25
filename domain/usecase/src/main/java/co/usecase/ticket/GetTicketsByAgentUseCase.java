package co.usecase.ticket;

import co.model.agentprofile.exception.AgentNotFoundException;
import co.model.agentprofile.gateways.AgentProfileRepository;
import co.model.ticket.Ticket;
import co.model.ticket.gateways.TicketRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class GetTicketsByAgentUseCase {
    private final TicketRepository ticketRepository;
    private final AgentProfileRepository agentProfileRepository;

    public List<Ticket> execute(UUID agentId) {
        if (agentId == null || agentProfileRepository.findByUserId(agentId) == null) {
            throw new AgentNotFoundException("Agent not found with id: " + agentId);
        }
        return ticketRepository.findByAgentId(agentId);
    }
}
