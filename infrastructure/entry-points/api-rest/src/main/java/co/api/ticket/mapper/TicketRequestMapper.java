package co.api.ticket.mapper;

import co.api.ticket.request.CreateTicketRequest;
import co.usecase.ticket.command.CreateTicketCommand;

import java.util.UUID;

public class TicketRequestMapper {

    public static CreateTicketCommand toCreateTicketCommand(CreateTicketRequest request, UUID customerId) {
        if(request == null) {
            return null;
        }
        return new CreateTicketCommand(
            request.title(),
            request.description(),
            customerId
        );
    }
}
