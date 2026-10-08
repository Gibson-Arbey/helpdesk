package co.usecase.ticket.command;

public record CreateTicketCommand(String title, String description, Long customerId) {
}
