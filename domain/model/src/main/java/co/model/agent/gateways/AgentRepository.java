package co.model.agent.gateways;

import co.model.agent.Agent;
import co.model.agentprofile.AgentStatus;

import java.util.UUID;

public interface AgentRepository {

    UUID findOneByStatus(AgentStatus status);
}
