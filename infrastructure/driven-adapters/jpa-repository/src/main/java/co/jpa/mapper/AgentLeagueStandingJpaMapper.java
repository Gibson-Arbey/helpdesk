package co.jpa.mapper;

import co.jpa.entity.AgentLeagueStandingJpaEntity;
import co.jpa.entity.AgentProfileJpaEntity;
import co.jpa.entity.LeagueSeasonJpaEntity;
import co.model.agentleaguestanding.AgentLeagueStanding;

public final class AgentLeagueStandingJpaMapper {

    private AgentLeagueStandingJpaMapper() {
    }

    public static AgentLeagueStanding toDomain(AgentLeagueStandingJpaEntity entity) {
        if (entity == null) return null;
        return AgentLeagueStanding.restore(
                entity.getId(),
                entity.getAgentProfile().getId(),
                entity.getLeagueSeason().getId(),
                entity.getPoints(),
                entity.getPosition()
        );
    }

    public static AgentLeagueStandingJpaEntity toEntity(AgentLeagueStanding standing) {
        if (standing == null) return null;
        AgentProfileJpaEntity agentProfile = new AgentProfileJpaEntity();
        agentProfile.setId(standing.getAgentProfileId());
        LeagueSeasonJpaEntity leagueSeason = new LeagueSeasonJpaEntity();
        leagueSeason.setId(standing.getLeagueSeasonId());
        return AgentLeagueStandingJpaEntity.builder()
                .id(standing.getId())
                .agentProfile(agentProfile)
                .leagueSeason(leagueSeason)
                .points(standing.getPoints())
                .position(standing.getPosition())
                .build();
    }
}
