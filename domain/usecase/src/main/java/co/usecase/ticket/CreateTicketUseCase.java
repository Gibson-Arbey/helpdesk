package co.usecase.ticket;

import co.model.ticket.Ticket;
import co.model.ticket.gateways.TicketRepository;
import co.model.ticketevent.TicketCreatedEvent;
import co.model.ticketevent.gateways.TicketEventRepository;
import co.usecase.ticket.command.CreateTicketCommand;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateTicketUseCase {

    private final TicketRepository ticketRepository;
    private final TicketEventRepository ticketEventRepository;


    public Ticket execute(CreateTicketCommand command) {
        Ticket ticket = Ticket.create(
                command.title(),
                command.description(),
                command.customerId()
        );

        Ticket savedTicket = ticketRepository.save(ticket);

        TicketCreatedEvent event = new TicketCreatedEvent(
                savedTicket.getId()
        );

        ticketEventRepository.publishTicketCreatedEvent(event);

        return savedTicket;
    }
}
