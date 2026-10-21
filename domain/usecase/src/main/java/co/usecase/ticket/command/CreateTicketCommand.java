package co.usecase.ticket.command;

import java.util.UUID;

public record CreateTicketCommand(String title, String description, UUID customerId) {
}
