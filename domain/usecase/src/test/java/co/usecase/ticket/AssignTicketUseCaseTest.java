package co.usecase.ticket;

import co.model.agentprofile.AgentProfile;
import co.model.agentprofile.exception.NoAvailableAgentException;
import co.model.ticket.Ticket;
import co.model.ticket.TicketStatus;
import co.model.ticket.TicketTransitionResult;
import co.model.ticket.exception.TicketNotFoundException;
import co.model.ticketevent.TicketAssignedEvent;
import co.model.ticketevent.gateways.TicketEventRepository;
import co.model.agentprofile.gateways.AgentProfileRepository;
import co.model.ticket.gateways.TicketRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AssignTicketUseCaseTest {

    @Mock
    private TicketRepository ticketRepository;
    @Mock
    private AgentProfileRepository agentProfileRepository;
    @Mock
    private TicketEventRepository ticketEventRepository;
    @InjectMocks
    private AssignTicketUseCase useCase;

    @Test
    void assignsAvailableAgentAndPublishesAfterPersistence() {
        UUID customerId = UUID.randomUUID();
        UUID profileId = UUID.randomUUID();
        UUID agentUserId = UUID.randomUUID();
        Ticket ticket = Ticket.restore(41L, "Payment", "Payment failed", customerId,
                TicketStatus.CREATED, LocalDateTime.now(), null, null);
        AgentProfile available = AgentProfile.restore(profileId, agentUserId, co.model.agentprofile.AgentStatus.AVAILABLE, null);
        when(ticketRepository.findById(41L)).thenReturn(ticket);
        when(agentProfileRepository.findFirstAvailable()).thenReturn(available);
        when(ticketRepository.saveTransition(any(Ticket.class), eq(TicketStatus.CREATED), any(AgentProfile.class)))
                .thenReturn(new TicketTransitionResult(
                        Ticket.restore(41L, "Payment", "Payment failed", customerId,
                                TicketStatus.ASSIGNED, ticket.getCreatedOn(), agentUserId, LocalDateTime.now()),
                        true
                ));

        Ticket assigned = useCase.execute(41L);

        assertEquals(TicketStatus.ASSIGNED, assigned.getStatus());
        assertEquals(agentUserId, assigned.getAgentId());
        ArgumentCaptor<Ticket> ticketCaptor = ArgumentCaptor.forClass(Ticket.class);
        ArgumentCaptor<AgentProfile> profileCaptor = ArgumentCaptor.forClass(AgentProfile.class);
        verify(ticketRepository).saveTransition(ticketCaptor.capture(), eq(TicketStatus.CREATED), profileCaptor.capture());
        assertEquals(TicketStatus.ASSIGNED, ticketCaptor.getValue().getStatus());
        assertEquals(co.model.agentprofile.AgentStatus.BUSY, profileCaptor.getValue().getStatus());
        verify(ticketEventRepository).publishTicketAssignedEvent(new TicketAssignedEvent(41L, agentUserId));
    }

    @Test
    void reportsMissingTicket() {
        when(ticketRepository.findById(77L)).thenReturn(null);

        assertThrows(TicketNotFoundException.class, () -> useCase.execute(77L));
        verify(agentProfileRepository, never()).findFirstAvailable();
        verify(ticketEventRepository, never()).publishTicketAssignedEvent(any());
    }

    @Test
    void reportsNoAvailableAgentWithoutChangingTicket() {
        Ticket ticket = Ticket.restore(41L, "Payment", "Payment failed", UUID.randomUUID(),
                TicketStatus.CREATED, LocalDateTime.now(), null, null);
        when(ticketRepository.findById(41L)).thenReturn(ticket);
        when(agentProfileRepository.findFirstAvailable()).thenReturn(null);

        assertThrows(NoAvailableAgentException.class, () -> useCase.execute(41L));
        verify(ticketRepository, never()).saveTransition(any(), any(), any());
        verify(ticketEventRepository, never()).publishTicketAssignedEvent(any());
    }

    @Test
    void redeliveredCreationEventDoesNotAssignTwice() {
        UUID agentId = UUID.randomUUID();
        Ticket alreadyAssigned = Ticket.restore(41L, "Payment", "Payment failed", UUID.randomUUID(),
                TicketStatus.ASSIGNED, LocalDateTime.now(), agentId, LocalDateTime.now());
        when(ticketRepository.findById(41L)).thenReturn(alreadyAssigned);

        Ticket result = useCase.execute(41L);

        assertSame(alreadyAssigned, result);
        verify(agentProfileRepository, never()).findFirstAvailable();
        verify(ticketRepository, never()).save(any());
        verify(ticketEventRepository, never()).publishTicketAssignedEvent(any());
    }
}
