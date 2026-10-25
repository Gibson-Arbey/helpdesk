package co.jpa.mapper;

import co.jpa.entity.LeagueJpaEntity;
import co.model.league.League;

public final class LeagueJpaMapper {

    private LeagueJpaMapper() {
    }

    public static League toDomain(LeagueJpaEntity entity) {
        if (entity == null) return null;
        return League.restore(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getMaximumNumberOfOpenTickets(),
                entity.getMaximumNumberOfPoints(),
                entity.getMinimumNumberOfPoints()
        );
    }

    public static LeagueJpaEntity toEntity(League league) {
        if (league == null) return null;
        LeagueJpaEntity entity = new LeagueJpaEntity();
        entity.setId(league.getId());
        entity.setName(league.getName());
        entity.setDescription(league.getDescription());
        entity.setMaximumNumberOfOpenTickets(league.getMaximumNumberOfOpenTickets());
        entity.setMaximumNumberOfPoints(league.getMaximumNumberOfPoints());
        entity.setMinimumNumberOfPoints(league.getMinimumNumberOfPoints());
        return entity;
    }
}
