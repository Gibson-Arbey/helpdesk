package co.api.ticket.response;

import java.time.LocalDateTime;
import java.util.UUID;

public record TicketResponse(Long id, String title, String description, UUID customerId, String status, LocalDateTime createdOn, UUID agentId, LocalDateTime assignedOn) {
}
