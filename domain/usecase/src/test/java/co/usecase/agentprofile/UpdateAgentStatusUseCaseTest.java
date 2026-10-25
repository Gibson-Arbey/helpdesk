package co.usecase.agentprofile;

import co.model.agentprofile.AgentProfile;
import co.model.agentprofile.AgentStatus;
import co.model.agentprofile.exception.AgentNotFoundException;
import co.model.agentprofile.gateways.AgentProfileRepository;
import co.model.exception.InvalidFieldException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UpdateAgentStatusUseCaseTest {

    @Mock
    private AgentProfileRepository agentProfileRepository;
    @InjectMocks
    private UpdateAgentStatusUseCase useCase;

    @Test
    void changesAnAvailableAgentToOffline() {
        UUID userId = UUID.randomUUID();
        AgentProfile profile = AgentProfile.create(userId);
        when(agentProfileRepository.findByUserId(userId)).thenReturn(profile);
        when(agentProfileRepository.save(org.mockito.ArgumentMatchers.any(AgentProfile.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        AgentProfile result = useCase.execute(userId, AgentStatus.OFFLINE);

        assertEquals(AgentStatus.OFFLINE, result.getStatus());
        verify(agentProfileRepository).save(result);
    }

    @Test
    void doesNotAllowManualBusyStatusOrUnknownAgent() {
        UUID userId = UUID.randomUUID();
        when(agentProfileRepository.findByUserId(userId)).thenReturn(AgentProfile.create(userId));

        assertThrows(InvalidFieldException.class, () -> useCase.execute(userId, AgentStatus.BUSY));
        verify(agentProfileRepository, never()).save(org.mockito.ArgumentMatchers.any());

        UUID missingId = UUID.randomUUID();
        when(agentProfileRepository.findByUserId(missingId)).thenReturn(null);
        assertThrows(AgentNotFoundException.class, () -> useCase.execute(missingId, AgentStatus.AVAILABLE));
    }
}
