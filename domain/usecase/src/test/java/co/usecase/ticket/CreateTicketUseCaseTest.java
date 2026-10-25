package co.usecase.ticket;

import co.model.ticket.Ticket;
import co.model.ticket.TicketStatus;
import co.model.ticket.gateways.TicketRepository;
import co.model.ticketevent.TicketCreatedEvent;
import co.model.ticketevent.gateways.TicketEventRepository;
import co.model.user.User;
import co.model.user.UserRole;
import co.model.user.exception.CustomerNotFoundException;
import co.model.user.gateways.UserRepository;
import co.model.user.valueobject.Email;
import co.model.user.valueobject.Password;
import co.usecase.ticket.command.CreateTicketCommand;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CreateTicketUseCaseTest {

    @Mock
    private TicketRepository ticketRepository;
    @Mock
    private TicketEventRepository ticketEventRepository;
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private CreateTicketUseCase useCase;

    @Test
    void persistsCreatedTicketBeforePublishingItsEvent() {
        UUID customerId = UUID.randomUUID();
        User customer = User.restore(customerId, "Customer", new Email("customer@example.com"), new Password("encoded"), UserRole.CUSTOMER);
        when(userRepository.findById(customerId)).thenReturn(customer);
        when(ticketRepository.save(any(Ticket.class))).thenAnswer(invocation -> {
            Ticket ticket = invocation.getArgument(0);
            return Ticket.restore(9L, ticket.getTitle(), ticket.getDescription(), ticket.getCustomerId(),
                    ticket.getStatus(), ticket.getCreatedOn(), ticket.getAgentId(), ticket.getAssignedOn());
        });

        Ticket created = useCase.execute(new CreateTicketCommand("Login", "Cannot log in", customerId));

        assertEquals(TicketStatus.CREATED, created.getStatus());
        assertEquals(9L, created.getId());
        ArgumentCaptor<Ticket> ticketCaptor = ArgumentCaptor.forClass(Ticket.class);
        verify(ticketRepository).save(ticketCaptor.capture());
        assertEquals(TicketStatus.CREATED, ticketCaptor.getValue().getStatus());
        verify(ticketEventRepository).publishTicketCreatedEvent(new TicketCreatedEvent(9L));
    }

    @Test
    void rejectsUnknownOrNonCustomerUsersBeforeSaving() {
        UUID customerId = UUID.randomUUID();
        User agent = User.restore(customerId, "Agent", new Email("agent@example.com"), new Password("encoded"), UserRole.AGENT);
        when(userRepository.findById(customerId)).thenReturn(agent);

        assertThrows(CustomerNotFoundException.class,
                () -> useCase.execute(new CreateTicketCommand("Login", "Cannot log in", customerId)));
        verify(ticketRepository, never()).save(any());
        verify(ticketEventRepository, never()).publishTicketCreatedEvent(any());
    }
}
