package co.jpa.adapter;

import co.jpa.mapper.AgentProfileJpaMapper;
import co.jpa.repository.AgentProfileJpaRepository;
import co.model.agentprofile.AgentProfile;
import co.model.agentprofile.gateways.AgentProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class AgentProfileJpaAdapter implements AgentProfileRepository {

    private final AgentProfileJpaRepository agentProfileJpaRepository;

    @Override
    public AgentProfile save(AgentProfile agentProfile) {
        return AgentProfileJpaMapper
                .toDomain(agentProfileJpaRepository
                        .save(AgentProfileJpaMapper.toEntity(agentProfile)));
    }

    @Override
    public AgentProfile findByUserId(UUID userId) {
        return AgentProfileJpaMapper
                .toDomain(agentProfileJpaRepository
                        .findByUserId(userId));
    }

    @Override
    public UUID findIdAgentProfileAvaliable() {
        return agentProfileJpaRepository.findIdByAgentProfileAvaliable();
    }
}
