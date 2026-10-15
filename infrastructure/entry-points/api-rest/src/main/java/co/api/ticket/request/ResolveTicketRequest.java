package co.api.ticket.request;

import jakarta.validation.constraints.NotNull;

public record ResolveTicketRequest(
        @NotNull(message = "Agent is required")
        Long agentId
) {}