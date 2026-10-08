package co.api.ticket.mapper;

import co.api.ticket.response.TicketResponse;
import co.model.ticket.Ticket;

public class TicketResponseMapper {

    public static TicketResponse toResponse(Ticket ticket) {
        if (ticket == null) {
            return null;
        }
        return new TicketResponse(
                ticket.getId(),
                ticket.getTitle(),
                ticket.getDescription(),
                ticket.getCustomerId(),
                ticket.getStatus().name(),
                ticket.getCreatedOn(),
                ticket.getAgentId(),
                ticket.getAssignedOn()
        );
    }
}
