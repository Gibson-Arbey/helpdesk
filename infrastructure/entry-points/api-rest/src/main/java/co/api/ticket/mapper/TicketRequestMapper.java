package co.api.ticket.mapper;

import co.api.ticket.request.CreateTicketRequest;
import co.usecase.ticket.command.CreateTicketCommand;

public class TicketRequestMapper {

    public CreateTicketCommand toCreateTicketCommand(CreateTicketRequest request) {
        if(request == null) {
            return null;
        }
        return new CreateTicketCommand(
            request.title(),
            request.description(),
            request.customerId()
        );
    }
}
