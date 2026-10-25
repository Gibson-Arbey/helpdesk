package co.model.agentprofile;

import co.model.exception.InvalidFieldException;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class AgentProfile {

    private final UUID id;
    private final UUID userId;
    private final AgentStatus status;
    private final LocalDateTime lastTicketClosedAt;

    private AgentProfile(UUID id, UUID userId, AgentStatus status, LocalDateTime lastTicketClosedAt) {
        if (id == null) throw new InvalidFieldException("AgentProfile id cannot be null");
        if (userId == null) throw new InvalidFieldException("AgentProfile userId cannot be null");
        if (status == null) throw new InvalidFieldException("AgentProfile status cannot be null");

        this.id = id;
        this.userId = userId;
        this.status = status;
        this.lastTicketClosedAt = lastTicketClosedAt;
    }

    public static AgentProfile create(UUID userId) {
        return new AgentProfile(UUID.randomUUID(), userId, AgentStatus.AVAILABLE, null);
    }

    public static AgentProfile restore(UUID id, UUID userId, AgentStatus status, LocalDateTime lastTicketClosedAt) {
        return new AgentProfile(id, userId, status, lastTicketClosedAt);
    }

    public AgentProfile markBusy() {
        if (status != AgentStatus.AVAILABLE) {
            throw new InvalidFieldException("Only an AVAILABLE agent can be assigned a ticket");
        }
        return new AgentProfile(id, userId, AgentStatus.BUSY, lastTicketClosedAt);
    }

    public AgentProfile completeTicket() {
        if (status != AgentStatus.BUSY) {
            throw new InvalidFieldException("Only a BUSY agent can complete a ticket");
        }
        return new AgentProfile(id, userId, AgentStatus.AVAILABLE, LocalDateTime.now());
    }

    public AgentProfile releaseCancelledTicket() {
        if (status != AgentStatus.BUSY) {
            throw new InvalidFieldException("Only a BUSY agent can release a cancelled ticket");
        }
        return new AgentProfile(id, userId, AgentStatus.AVAILABLE, lastTicketClosedAt);
    }

    public AgentProfile changeStatus(AgentStatus newStatus) {
        if (newStatus == null) {
            throw new InvalidFieldException("Agent status cannot be null");
        }
        if (newStatus == AgentStatus.BUSY) {
            throw new InvalidFieldException("BUSY is managed by ticket assignment");
        }
        if (status == AgentStatus.BUSY) {
            throw new InvalidFieldException("An agent with an active ticket cannot change status");
        }
        return new AgentProfile(id, userId, newStatus, lastTicketClosedAt);
    }
}
