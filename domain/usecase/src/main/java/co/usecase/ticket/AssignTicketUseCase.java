package co.usecase.ticket;

import co.model.agent.gateways.AgentRepository;
import co.model.ticket.gateways.TicketRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AssignTicketUseCase {

    public final TicketRepository ticketRepository;
    private final AgentRepository agentRepository;

    public void execute(Long ticketId) {
    }
}
