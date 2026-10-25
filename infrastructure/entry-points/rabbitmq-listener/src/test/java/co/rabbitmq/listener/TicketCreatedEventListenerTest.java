package co.rabbitmq.listener;

import co.model.agentprofile.exception.NoAvailableAgentException;
import co.model.ticket.exception.TicketNotFoundException;
import co.model.ticketevent.TicketCreatedEvent;
import co.usecase.ticket.AssignTicketUseCase;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TicketCreatedEventListenerTest {

    @Mock
    private AssignTicketUseCase assignTicketUseCase;
    @InjectMocks
    private TicketCreatedEventListener listener;

    @Test
    void delegatesValidCreationEventToAssignmentUseCase() {
        listener.handleTicketCreatedEvent(new TicketCreatedEvent(8L));

        verify(assignTicketUseCase).execute(8L);
    }

    @Test
    void rejectsMalformedEventWithoutRetry() {
        assertThrows(AmqpRejectAndDontRequeueException.class,
                () -> listener.handleTicketCreatedEvent(new TicketCreatedEvent(null)));
    }

    @Test
    void allowsCapacityErrorsToUseTheConfiguredFiniteRetryPolicy() {
        when(assignTicketUseCase.execute(8L)).thenThrow(new NoAvailableAgentException("No agents"));

        assertThrows(NoAvailableAgentException.class,
                () -> listener.handleTicketCreatedEvent(new TicketCreatedEvent(8L)));
    }

    @Test
    void sendsPermanentBusinessErrorsDirectlyToDeadLetterQueue() {
        when(assignTicketUseCase.execute(8L)).thenThrow(new TicketNotFoundException("Missing"));

        assertThrows(AmqpRejectAndDontRequeueException.class,
                () -> listener.handleTicketCreatedEvent(new TicketCreatedEvent(8L)));
    }
}
