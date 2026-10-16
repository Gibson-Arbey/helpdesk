package co.model.agentprofile.gateways;

import co.model.agentprofile.AgentProfile;

import java.util.UUID;

public interface AgentProfileRepository {

    AgentProfile save(AgentProfile agentProfile);

    AgentProfile findByUserId(UUID userId);
}
