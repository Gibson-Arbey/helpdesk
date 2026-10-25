package co.jpa.mapper;

import co.jpa.entity.AgentLeagueStandingJpaEntity;
import co.jpa.entity.AgentProfileJpaEntity;
import co.jpa.entity.LeagueJpaEntity;
import co.jpa.entity.LeagueSeasonJpaEntity;
import co.model.agentprofile.AgentStatus;
import co.model.league.League;
import co.model.leagueseason.LeagueSeason;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class LeagueMappingsTest {

    @Test
    void mapsLeagueLimitsAndIdentity() {
        UUID id = UUID.randomUUID();
        League league = League.restore(id, "Bronce", "Starter league", 5, 100, 0);

        var entity = LeagueJpaMapper.toEntity(league);
        var mappedLeague = LeagueJpaMapper.toDomain(entity);

        assertEquals(id, entity.getId());
        assertEquals("Bronce", mappedLeague.getName());
        assertEquals(5, mappedLeague.getMaximumNumberOfOpenTickets());
        assertEquals(100, mappedLeague.getMaximumNumberOfPoints());
        assertEquals(0, mappedLeague.getMinimumNumberOfPoints());
    }

    @Test
    void mapsLeagueSeasonAndLeagueForeignKey() {
        UUID leagueId = UUID.randomUUID();
        LeagueJpaEntity league = new LeagueJpaEntity();
        league.setId(leagueId);
        LeagueSeasonJpaEntity entity = LeagueSeasonJpaEntity.builder()
                .id(UUID.randomUUID())
                .league(league)
                .name("Season 2026")
                .startDate(LocalDate.of(2026, 1, 1))
                .build();

        LeagueSeason season = LeagueSeasonJpaMapper.toDomain(entity);
        LeagueSeasonJpaEntity mappedEntity = LeagueSeasonJpaMapper.toEntity(season);

        assertEquals(leagueId, season.getLeagueId());
        assertEquals(season.getId(), mappedEntity.getId());
        assertEquals(leagueId, mappedEntity.getLeague().getId());
        assertNull(mappedEntity.getEndDate());
    }

    @Test
    void mapsStandingToAgentProfileAndSeasonIds() {
        UUID profileId = UUID.randomUUID();
        UUID seasonId = UUID.randomUUID();
        AgentProfileJpaEntity profile = AgentProfileJpaEntity.builder()
                .id(profileId)
                .status(AgentStatus.AVAILABLE)
                .build();
        LeagueSeasonJpaEntity season = LeagueSeasonJpaEntity.builder()
                .id(seasonId)
                .name("Season 2026")
                .startDate(LocalDate.of(2026, 1, 1))
                .build();
        AgentLeagueStandingJpaEntity entity = AgentLeagueStandingJpaEntity.builder()
                .id(UUID.randomUUID())
                .agentProfile(profile)
                .leagueSeason(season)
                .points(32)
                .position(3)
                .build();

        var standing = AgentLeagueStandingJpaMapper.toDomain(entity);
        var mappedEntity = AgentLeagueStandingJpaMapper.toEntity(standing);

        assertEquals(profileId, standing.getAgentProfileId());
        assertEquals(seasonId, standing.getLeagueSeasonId());
        assertEquals(32, mappedEntity.getPoints());
        assertEquals(3, mappedEntity.getPosition());
        assertEquals(profileId, mappedEntity.getAgentProfile().getId());
        assertEquals(seasonId, mappedEntity.getLeagueSeason().getId());
    }
}
