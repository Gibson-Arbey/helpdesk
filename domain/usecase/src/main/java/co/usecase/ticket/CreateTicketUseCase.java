package co.usecase.ticket;

import co.model.ticket.Ticket;
import co.model.ticket.gateways.TicketRepository;
import co.usecase.ticket.command.CreateTicketCommand;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateTicketUseCase {

    private final TicketRepository ticketRepository;

    public Ticket execute(CreateTicketCommand command){
        Ticket ticket = Ticket.create(command.title(), command.description(), command.customerId());
        return ticketRepository.save(ticket);
    }
}
