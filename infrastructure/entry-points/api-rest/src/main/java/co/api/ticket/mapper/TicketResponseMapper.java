package co.api.ticket.mapper;

import co.api.ticket.response.TicketResponse;
import co.model.ticket.Ticket;

import java.util.List;

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

    public static List<TicketResponse> toResponses(List<Ticket> tickets) {
        if (tickets == null) {
            return List.of();
        }
        return tickets.stream().map(TicketResponseMapper::toResponse).toList();
    }
}
