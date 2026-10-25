package co.model.league.gateways;

import co.model.league.League;

import java.util.List;
import java.util.UUID;

public interface LeagueRepository {

    League save(League league);

    League findById(UUID id);

    List<League> findAll();
}
