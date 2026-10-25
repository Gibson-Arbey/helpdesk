package co.model.ticket;

import co.model.exception.InvalidFieldException;
import co.model.ticket.exception.InvalidTicketStatusException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TicketTest {

    private final UUID customerId = UUID.randomUUID();
    private final UUID agentId = UUID.randomUUID();

    @Test
    void createdTicketFollowsTheExistingResolutionAndClosureFlow() {
        Ticket ticket = Ticket.create("Login issue", "Cannot sign in", customerId);

        Ticket assigned = ticket.assignAgent(agentId);
        Ticket inProgress = assigned.start();
        Ticket resolved = inProgress.resolve();
        Ticket closed = resolved.close();

        assertEquals(TicketStatus.CREATED, ticket.getStatus());
        assertEquals(TicketStatus.ASSIGNED, assigned.getStatus());
        assertEquals(TicketStatus.IN_PROGRESS, inProgress.getStatus());
        assertEquals(TicketStatus.RESOLVED, resolved.getStatus());
        assertEquals(TicketStatus.CLOSED, closed.getStatus());
        assertEquals(agentId, closed.getAgentId());
        assertEquals(customerId, closed.getCustomerId());
    }

    @Test
    void onlyCreatedOrAssignedTicketsCanBeCancelled() {
        Ticket created = Ticket.create("Login issue", "Cannot sign in", customerId);
        Ticket cancelledCreated = created.cancel();
        Ticket assigned = created.assignAgent(agentId);
        Ticket cancelledAssigned = assigned.cancel();

        assertEquals(TicketStatus.CANCELLED, cancelledCreated.getStatus());
        assertEquals(TicketStatus.CANCELLED, cancelledAssigned.getStatus());
        assertEquals(agentId, cancelledAssigned.getAgentId());
        assertThrows(InvalidTicketStatusException.class, () -> assigned.start().cancel());
    }

    @Test
    void rejectsInvalidTransitionsAndRequiredData() {
        Ticket created = Ticket.create("Login issue", "Cannot sign in", customerId);

        assertThrows(InvalidTicketStatusException.class, created::start);
        assertThrows(InvalidTicketStatusException.class, created::close);
        assertThrows(InvalidFieldException.class, () -> Ticket.create(" ", "Description", customerId));
        assertThrows(InvalidFieldException.class, () -> Ticket.create("Title", "Description", null));
    }

    @Test
    void restoreRejectsAnAssignmentWithoutItsTimestamp() {
        assertThrows(InvalidFieldException.class, () -> Ticket.restore(
                1L,
                "Login issue",
                "Cannot sign in",
                customerId,
                TicketStatus.ASSIGNED,
                LocalDateTime.now(),
                agentId,
                null
        ));
    }
}
