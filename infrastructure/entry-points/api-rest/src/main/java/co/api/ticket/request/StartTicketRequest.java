package co.api.ticket.request;

import jakarta.validation.constraints.NotNull;

public record StartTicketRequest(
        @NotNull(message = "Agent is required") Long agentId
) {
}
