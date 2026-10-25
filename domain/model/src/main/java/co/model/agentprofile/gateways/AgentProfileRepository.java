package co.model.agentprofile.gateways;

import co.model.agentprofile.AgentProfile;
import co.model.agentprofile.AgentStatus;

import java.util.List;
import java.util.UUID;

public interface AgentProfileRepository {

    AgentProfile save(AgentProfile agentProfile);

    AgentProfile findByUserId(UUID userId);

    AgentProfile findFirstAvailable();

    List<AgentProfile> findByStatus(AgentStatus status);
}
