package co.jpa.adapter;

import co.jpa.mapper.AgentProfileJpaMapper;
import co.jpa.repository.AgentProfileJpaRepository;
import co.model.agentprofile.AgentProfile;
import co.model.agentprofile.AgentStatus;
import co.model.exception.InvalidFieldException;
import co.model.agentprofile.gateways.AgentProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class AgentProfileJpaAdapter implements AgentProfileRepository {

    private final AgentProfileJpaRepository agentProfileJpaRepository;

    @Override
    @Transactional
    public AgentProfile save(AgentProfile agentProfile) {
        var existing = agentProfileJpaRepository.findByIdForUpdate(agentProfile.getId()).orElse(null);
        if (existing == null) {
            return AgentProfileJpaMapper.toDomain(
                    agentProfileJpaRepository.save(AgentProfileJpaMapper.toEntity(agentProfile))
            );
        }
        if (!existing.getUser().getId().equals(agentProfile.getUserId())) {
            throw new InvalidFieldException("Agent profile user cannot be changed");
        }
        if (existing.getStatus() == AgentStatus.BUSY && agentProfile.getStatus() != AgentStatus.BUSY) {
            throw new InvalidFieldException("A BUSY agent can only become AVAILABLE when its ticket is completed");
        }
        if (existing.getStatus() != AgentStatus.BUSY && agentProfile.getStatus() == AgentStatus.BUSY) {
            throw new InvalidFieldException("BUSY status is managed by ticket assignment");
        }

        existing.setStatus(agentProfile.getStatus());
        existing.setLastTicketClosedAt(agentProfile.getLastTicketClosedAt());
        return AgentProfileJpaMapper.toDomain(existing);
    }

    @Override
    @Transactional(readOnly = true)
    public AgentProfile findByUserId(UUID userId) {
        return AgentProfileJpaMapper
                .toDomain(agentProfileJpaRepository
                        .findByUserId(userId).orElse(null));
    }

    @Override
    @Transactional(readOnly = true)
    public AgentProfile findFirstAvailable() {
        return agentProfileJpaRepository
                .findFirstByStatusOrderByIdAsc(AgentStatus.AVAILABLE)
                .map(AgentProfileJpaMapper::toDomain)
                .orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AgentProfile> findByStatus(AgentStatus status) {
        return agentProfileJpaRepository.findByStatusOrderByIdAsc(status).stream()
                .map(AgentProfileJpaMapper::toDomain)
                .collect(Collectors.toList());
    }
}
