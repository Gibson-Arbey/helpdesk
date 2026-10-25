package co.jpa.adapter;

import co.jpa.mapper.AgentLeagueStandingJpaMapper;
import co.jpa.repository.AgentLeagueStandingJpaRepository;
import co.model.agentleaguestanding.AgentLeagueStanding;
import co.model.agentleaguestanding.gateways.AgentLeagueStandingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class AgentLeagueStandingJpaAdapter implements AgentLeagueStandingRepository {

    private final AgentLeagueStandingJpaRepository agentLeagueStandingJpaRepository;

    @Override
    @Transactional
    public AgentLeagueStanding save(AgentLeagueStanding standing) {
        return AgentLeagueStandingJpaMapper.toDomain(
                agentLeagueStandingJpaRepository.save(AgentLeagueStandingJpaMapper.toEntity(standing))
        );
    }

    @Override
    @Transactional(readOnly = true)
    public AgentLeagueStanding findById(UUID id) {
        return AgentLeagueStandingJpaMapper.toDomain(agentLeagueStandingJpaRepository.findById(id).orElse(null));
    }

    @Override
    @Transactional(readOnly = true)
    public AgentLeagueStanding findByAgentProfileIdAndLeagueSeasonId(UUID agentProfileId, UUID leagueSeasonId) {
        return AgentLeagueStandingJpaMapper.toDomain(
                agentLeagueStandingJpaRepository.findByAgentProfile_IdAndLeagueSeason_Id(agentProfileId, leagueSeasonId)
                        .orElse(null)
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<AgentLeagueStanding> findByLeagueSeasonId(UUID leagueSeasonId) {
        return agentLeagueStandingJpaRepository.findByLeagueSeason_IdOrderByPositionAscPointsDesc(leagueSeasonId).stream()
                .map(AgentLeagueStandingJpaMapper::toDomain)
                .toList();
    }
}
