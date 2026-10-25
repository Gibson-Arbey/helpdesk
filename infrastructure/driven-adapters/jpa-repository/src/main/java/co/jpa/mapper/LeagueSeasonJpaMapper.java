package co.jpa.mapper;

import co.jpa.entity.LeagueJpaEntity;
import co.jpa.entity.LeagueSeasonJpaEntity;
import co.model.leagueseason.LeagueSeason;

public final class LeagueSeasonJpaMapper {

    private LeagueSeasonJpaMapper() {
    }

    public static LeagueSeason toDomain(LeagueSeasonJpaEntity entity) {
        if (entity == null) return null;
        return LeagueSeason.restore(
                entity.getId(),
                entity.getLeague().getId(),
                entity.getName(),
                entity.getStartDate(),
                entity.getEndDate()
        );
    }

    public static LeagueSeasonJpaEntity toEntity(LeagueSeason season) {
        if (season == null) return null;
        LeagueJpaEntity league = new LeagueJpaEntity();
        league.setId(season.getLeagueId());
        return LeagueSeasonJpaEntity.builder()
                .id(season.getId())
                .league(league)
                .name(season.getName())
                .startDate(season.getStartDate())
                .endDate(season.getEndDate())
                .build();
    }
}
