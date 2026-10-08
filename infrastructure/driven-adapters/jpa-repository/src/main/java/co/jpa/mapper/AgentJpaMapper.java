package co.jpa.mapper;

import co.jpa.entity.AgentJpaEntity;
import co.model.agent.Agent;

public class AgentJpaMapper {

    public static Agent toDomain(AgentJpaEntity entity) {
        if(entity == null) {
            return null;
        }
        return Agent.restore(entity.getId(), entity.getName(), entity.getEmail(), entity.getStatus());
    }

    public static AgentJpaEntity toEntity(Agent agent) {
        if(agent == null) {
            return null;
        }
        return AgentJpaEntity.builder()
                .id(agent.getId())
                .name(agent.getName())
                .email(agent.getEmail())
                .status(agent.getStatus())
                .build();
    }
}
