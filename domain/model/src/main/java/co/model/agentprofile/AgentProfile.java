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
}
