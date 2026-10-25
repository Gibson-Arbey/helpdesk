package co.usecase.ticket;

import co.model.exception.InvalidFieldException;
import co.model.ticket.Ticket;
import co.model.ticket.gateways.TicketRepository;
import co.model.ticketevent.TicketCreatedEvent;
import co.model.ticketevent.gateways.TicketEventRepository;
import co.model.user.User;
import co.model.user.UserRole;
import co.model.user.exception.CustomerNotFoundException;
import co.model.user.gateways.UserRepository;
import co.usecase.ticket.command.CreateTicketCommand;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateTicketUseCase {

    private final TicketRepository ticketRepository;
    private final TicketEventRepository ticketEventRepository;
    private final UserRepository userRepository;

    public Ticket execute(CreateTicketCommand command) {
        if (command == null || command.customerId() == null) {
            throw new InvalidFieldException("A customer is required to create a ticket");
        }

        User customer = userRepository.findById(command.customerId());
        if (customer == null || customer.getRole() != UserRole.CUSTOMER) {
            throw new CustomerNotFoundException("Customer not found with id: " + command.customerId());
        }

        Ticket ticket = Ticket.create(command.title(), command.description(), command.customerId());
        Ticket savedTicket = ticketRepository.save(ticket);
        if (savedTicket == null || savedTicket.getId() == null) {
            throw new IllegalStateException("Ticket repository did not return a persisted ticket id");
        }

        ticketEventRepository.publishTicketCreatedEvent(new TicketCreatedEvent(savedTicket.getId()));
        return savedTicket;
    }
}
