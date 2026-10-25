package co.model.leagueseason.gateways;

import co.model.leagueseason.LeagueSeason;
import co.model.leagueseason.LeagueSeasonCycleResult;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface LeagueSeasonRepository {

    LeagueSeason save(LeagueSeason leagueSeason);

    List<LeagueSeason> saveAll(List<LeagueSeason> leagueSeasons);

    LeagueSeasonCycleResult applyCycle(List<LeagueSeason> seasonsToExpire, List<LeagueSeason> seasonsToStart);

    LeagueSeason findById(UUID id);

    List<LeagueSeason> findByLeagueId(UUID leagueId);

    List<LeagueSeason> findActiveStartedOnOrBefore(LocalDate cutoffDate);
}
