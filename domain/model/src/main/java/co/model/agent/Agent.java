package co.model.agent;

import co.model.agentprofile.AgentStatus;
import lombok.Getter;

@Getter
public class Agent {

    private final Long id;
    private final String name;
    private final String email;
    private final AgentStatus status;

    private Agent(Long id, String name, String email, AgentStatus status) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.status = status;
    }

    public static Agent create(String name, String email, AgentStatus status) {
        return  new Agent(null, name, email, status);
    }

    public static Agent restore(Long id, String name, String email, AgentStatus status) {
        return new Agent(id, name, email, status);
    }
}
