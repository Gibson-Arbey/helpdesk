package co.api.ticket.request;

import jakarta.validation.constraints.NotNull;

public record CloseTicketRequest(
        @NotNull(message = "Customer is required")
        Long customerId
) {}