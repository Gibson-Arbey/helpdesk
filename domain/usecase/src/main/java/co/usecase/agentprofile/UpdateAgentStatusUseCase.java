package co.usecase.agentprofile;

import co.model.agentprofile.AgentProfile;
import co.model.agentprofile.AgentStatus;
import co.model.agentprofile.exception.AgentNotFoundException;
import co.model.agentprofile.gateways.AgentProfileRepository;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class UpdateAgentStatusUseCase {
    private final AgentProfileRepository agentProfileRepository;

    public AgentProfile execute(UUID userId, AgentStatus status) {
        AgentProfile agentProfile = agentProfileRepository.findByUserId(userId);
        if (agentProfile == null) {
            throw new AgentNotFoundException("Agent not found with user id: " + userId);
        }
        return agentProfileRepository.save(agentProfile.changeStatus(status));
    }
}
