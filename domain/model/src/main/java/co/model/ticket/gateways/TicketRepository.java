package co.model.ticket.gateways;

import co.model.agentprofile.AgentProfile;
import co.model.ticket.Ticket;
import co.model.ticket.TicketStatus;
import co.model.ticket.TicketTransitionResult;

import java.util.List;
import java.util.UUID;

public interface TicketRepository {

    Ticket save(Ticket ticket);

    TicketTransitionResult saveTransition(Ticket ticket, TicketStatus expectedStatus, AgentProfile updatedAgentProfile);

    Ticket findById(Long id);

    List<Ticket> findAll();

    List<Ticket> findByAgentId(UUID agentId);

    List<Ticket> findByCustomerId(UUID customerId);

}
