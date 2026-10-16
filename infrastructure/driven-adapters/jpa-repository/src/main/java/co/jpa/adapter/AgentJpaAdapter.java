package co.jpa.adapter;

import co.jpa.mapper.AgentJpaMapper;
import co.jpa.repository.AgentJpaRepository;
import co.model.agent.Agent;
import co.model.agentprofile.AgentStatus;
import co.model.agent.gateways.AgentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;


@Repository
@RequiredArgsConstructor
public class AgentJpaAdapter implements AgentRepository {

    private final AgentJpaRepository agentJpaRepository;

    @Override
    public Agent findOneByStatus(AgentStatus status) {
        return agentJpaRepository.findOneByStatus(status)
                .map(AgentJpaMapper::toDomain)
                .orElse(null);
    }
}
