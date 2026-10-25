package co.model.ticketevent;

import java.util.UUID;

public record TicketAssignedEvent(Long id, UUID agentId) {
}
