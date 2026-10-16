package co.jpa.mapper;

import co.jpa.entity.AgentProfileJpaEntity;
import co.jpa.entity.UserJpaEntity;
import co.model.agentprofile.AgentProfile;

public class AgentProfileJpaMapper {

    public static AgentProfile toDomain(AgentProfileJpaEntity entity) {
        if (entity == null) {
            return null;
        }
        return AgentProfile.restore(
                entity.getId(),
                entity.getUser().getId(),
                entity.getStatus(),
                entity.getLastTicketClosedAt()
        );
    }

    public static AgentProfileJpaEntity toEntity(AgentProfile agentProfile) {
        if (agentProfile == null) {
            return null;
        }
        return AgentProfileJpaEntity.builder()
                .id(agentProfile.getId())
                .user(UserJpaEntity.builder().id(agentProfile.getUserId()).build())
                .status(agentProfile.getStatus())
                .lastTicketClosedAt(agentProfile.getLastTicketClosedAt())
                .build();
    }
}
