package co.api.agent;

import co.api.config.filter.AuthenticationUser;
import co.model.agentprofile.AgentProfile;
import co.usecase.agentprofile.GetAvailableAgentsUseCase;
import co.usecase.agentprofile.UpdateAgentStatusUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/agents")
public class AgentApiRest {

    private final GetAvailableAgentsUseCase getAvailableAgentsUseCase;
    private final UpdateAgentStatusUseCase updateAgentStatusUseCase;

    @GetMapping("/available")
    public ResponseEntity<List<AgentProfileResponse>> getAvailableAgents() {
        List<AgentProfileResponse> agents = getAvailableAgentsUseCase.execute().stream()
                .map(AgentApiRest::toResponse)
                .toList();
        return ResponseEntity.ok(agents);
    }

    @PatchMapping("/me/status")
    public ResponseEntity<AgentProfileResponse> updateMyStatus(
            @AuthenticationPrincipal AuthenticationUser authenticatedUser,
            @Valid @RequestBody AgentStatusRequest request
    ) {
        AgentProfile updated = updateAgentStatusUseCase.execute(authenticatedUser.userId(), request.status());
        return ResponseEntity.ok(toResponse(updated));
    }

    private static AgentProfileResponse toResponse(AgentProfile profile) {
        return new AgentProfileResponse(profile.getId(), profile.getUserId(), profile.getStatus(), profile.getLastTicketClosedAt());
    }
}
