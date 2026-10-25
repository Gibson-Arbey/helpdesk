package co.usecase.ticket;

import co.model.ticket.Ticket;
import co.model.ticket.gateways.TicketRepository;
import co.model.user.User;
import co.model.user.UserRole;
import co.model.user.exception.CustomerNotFoundException;
import co.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public class GetTicketsByCustomerUseCase {
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    public List<Ticket> execute(UUID customerId) {
        User customer = customerId == null ? null : userRepository.findById(customerId);
        if (customer == null || customer.getRole() != UserRole.CUSTOMER) {
            throw new CustomerNotFoundException("Customer not found with id: " + customerId);
        }
        return ticketRepository.findByCustomerId(customerId);
    }
}
