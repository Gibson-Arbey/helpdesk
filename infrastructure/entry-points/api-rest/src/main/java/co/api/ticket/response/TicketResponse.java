package co.api.ticket.response;

import java.time.LocalDateTime;

public record TicketResponse(Long id, String title, String description, Long customerId, String status, LocalDateTime createdOn, Long agentId, LocalDateTime assignedOn) {
}
