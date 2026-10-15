package co.model.agent.gateways;

import co.model.agent.Agent;
import co.model.agent.AgentStatus;

public interface AgentRepository {

    Agent findOneByStatus(AgentStatus status);
}
