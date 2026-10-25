package co.model.agentprofile;

import co.model.exception.InvalidFieldException;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AgentProfileTest {

    @Test
    void assignmentAndCompletionKeepAgentAvailabilityConsistent() {
        AgentProfile profile = AgentProfile.create(UUID.randomUUID());

        AgentProfile busy = profile.markBusy();
        AgentProfile available = busy.completeTicket();

        assertEquals(AgentStatus.BUSY, busy.getStatus());
        assertEquals(AgentStatus.AVAILABLE, available.getStatus());
        assertNotNull(available.getLastTicketClosedAt());
    }

    @Test
    void manualStatusChangesCannotOverrideAssignmentState() {
        AgentProfile available = AgentProfile.create(UUID.randomUUID());
        AgentProfile offline = available.changeStatus(AgentStatus.OFFLINE);

        assertEquals(AgentStatus.OFFLINE, offline.getStatus());
        assertThrows(InvalidFieldException.class, () -> available.changeStatus(AgentStatus.BUSY));
        assertThrows(InvalidFieldException.class, () -> available.markBusy().changeStatus(AgentStatus.AVAILABLE));
        assertThrows(InvalidFieldException.class, () -> AgentProfile.restore(
                UUID.randomUUID(), UUID.randomUUID(), AgentStatus.AVAILABLE, null
        ).markBusy().markBusy());
    }
}
