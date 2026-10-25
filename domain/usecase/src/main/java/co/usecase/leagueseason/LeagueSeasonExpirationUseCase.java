package co.usecase.leagueseason;

import co.model.league.League;
import co.model.league.gateways.LeagueRepository;
import co.model.leagueseason.LeagueSeason;
import co.model.leagueseason.LeagueSeasonCycleResult;
import co.model.leagueseason.gateways.LeagueSeasonRepository;
import lombok.RequiredArgsConstructor;

import java.time.Clock;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class LeagueSeasonExpirationUseCase {

    private final LeagueRepository leagueRepository;
    private final LeagueSeasonRepository leagueSeasonRepository;
    private final Clock clock;

    public LeagueSeasonCycleResult execute() {
        LocalDate today = LocalDate.now(clock);
        YearMonth currentMonth = YearMonth.from(today);
        LocalDate expirationDate = currentMonth.atDay(1).minusDays(1);

        List<LeagueSeason> seasonsToExpire = leagueSeasonRepository
                .findActiveStartedOnOrBefore(expirationDate);
        Set<UUID> expiringSeasonIds = seasonsToExpire.stream()
                .map(LeagueSeason::getId)
                .collect(Collectors.toSet());
        List<LeagueSeason> expiredSeasons = seasonsToExpire.stream()
                .map(season -> season.expire(expirationDate))
                .toList();

        String seasonName = currentMonth.toString();
        LocalDate seasonStartDate = currentMonth.atDay(1);
        List<LeagueSeason> seasonsToStart = leagueRepository.findAll().stream()
                .flatMap(league -> newLeagueSeasonIfEligible(
                        league, seasonName, seasonStartDate, expiringSeasonIds
                ).stream())
                .toList();

        return leagueSeasonRepository.applyCycle(expiredSeasons, seasonsToStart);
    }

    private Optional<LeagueSeason> newLeagueSeasonIfEligible(
            League league,
            String seasonName,
            LocalDate seasonStartDate,
            Set<UUID> expiringSeasonIds
    ) {
        List<LeagueSeason> existingSeasons = leagueSeasonRepository.findByLeagueId(league.getId());
        boolean alreadyStartedForMonth = existingSeasons.stream()
                .anyMatch(season -> seasonName.equals(season.getName()));
        boolean anotherSeasonRemainsActive = existingSeasons.stream()
                .anyMatch(season -> season.isActive() && !expiringSeasonIds.contains(season.getId()));

        if (alreadyStartedForMonth || anotherSeasonRemainsActive) {
            return Optional.empty();
        }
        return Optional.of(LeagueSeason.create(league.getId(), seasonName, seasonStartDate));
    }
}
