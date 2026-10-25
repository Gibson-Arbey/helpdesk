package co.jpa.repository;

import co.jpa.entity.AgentLeagueStandingJpaEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AgentLeagueStandingJpaRepository extends JpaRepository<AgentLeagueStandingJpaEntity, UUID> {

    @EntityGraph(attributePaths = {"agentProfile", "leagueSeason"})
    Optional<AgentLeagueStandingJpaEntity> findByAgentProfile_IdAndLeagueSeason_Id(UUID agentProfileId, UUID leagueSeasonId);

    @EntityGraph(attributePaths = {"agentProfile", "leagueSeason"})
    List<AgentLeagueStandingJpaEntity> findByLeagueSeason_IdOrderByPositionAscPointsDesc(UUID leagueSeasonId);
}
