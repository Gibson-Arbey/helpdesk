package co.usecase.ticket;

import co.model.ticket.Ticket;
import co.model.ticket.gateways.TicketRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GetTicketsUseCase {
    private final TicketRepository ticketRepository;

    public List<Ticket> execute() {
        return ticketRepository.findAll();
    }
}
