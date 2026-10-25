package co.api.agent;

import co.model.agentprofile.AgentStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record AgentProfileResponse(UUID id, UUID userId, AgentStatus status, LocalDateTime lastTicketClosedAt) {
}
