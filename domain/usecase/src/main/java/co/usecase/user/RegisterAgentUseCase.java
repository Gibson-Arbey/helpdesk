package co.usecase.user;

import co.model.agentprofile.AgentProfile;
import co.model.agentprofile.gateways.AgentProfileRepository;
import co.model.user.User;
import co.model.user.UserRole;
import co.usecase.user.command.CreateUserCommand;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RegisterAgentUseCase {

    private final CreateUserUseCase createUserUseCase;
    private final AgentProfileRepository agentProfileRepository;

    public User execute(CreateUserCommand command) {
        User user =  createUserUseCase.execute(command, UserRole.AGENT);
        AgentProfile agentProfile = AgentProfile.create(user.getId());
        agentProfileRepository.save(agentProfile);
        return user;
    }
}
