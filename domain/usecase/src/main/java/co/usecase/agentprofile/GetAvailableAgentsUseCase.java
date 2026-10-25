package co.usecase.agentprofile;

import co.model.agentprofile.AgentProfile;
import co.model.agentprofile.AgentStatus;
import co.model.agentprofile.gateways.AgentProfileRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class GetAvailableAgentsUseCase {
    private final AgentProfileRepository agentProfileRepository;

    public List<AgentProfile> execute() {
        return agentProfileRepository.findByStatus(AgentStatus.AVAILABLE);
    }
}
