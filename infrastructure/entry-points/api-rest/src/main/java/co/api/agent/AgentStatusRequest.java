package co.api.agent;

import co.model.agentprofile.AgentStatus;
import jakarta.validation.constraints.NotNull;

public record AgentStatusRequest(@NotNull AgentStatus status) {
}
