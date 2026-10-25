package co.usecase.leagueseason;

import co.model.league.League;
import co.model.league.gateways.LeagueRepository;
import co.model.leagueseason.LeagueSeason;
import co.model.leagueseason.LeagueSeasonCycleResult;
import co.model.leagueseason.gateways.LeagueSeasonRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LeagueSeasonExpirationUseCaseTest {

    private static final ZoneId ZONE = ZoneId.of("America/Bogota");
    private static final LocalDate TODAY = LocalDate.of(2026, 10, 1);

    @Mock
    private LeagueRepository leagueRepository;

    @Mock
    private LeagueSeasonRepository leagueSeasonRepository;

    @Test
    void expiresPreviousSeasonAndStartsCurrentMonthForEachEligibleLeague() {
        Clock clock = Clock.fixed(TODAY.atStartOfDay(ZONE).toInstant(), ZONE);
        LocalDate expirationDate = LocalDate.of(2026, 9, 30);
        UUID leagueId = UUID.randomUUID();
        League league = league(leagueId);
        LeagueSeason previousSeason = LeagueSeason.create(leagueId, "2026-09", LocalDate.of(2026, 9, 1));
        when(leagueSeasonRepository.findActiveStartedOnOrBefore(expirationDate)).thenReturn(List.of(previousSeason));
        when(leagueRepository.findAll()).thenReturn(List.of(league));
        when(leagueSeasonRepository.findByLeagueId(leagueId)).thenReturn(List.of(previousSeason));
        when(leagueSeasonRepository.applyCycle(anyList(), anyList())).thenAnswer(invocation ->
                new LeagueSeasonCycleResult(
                        ((List<?>) invocation.getArgument(0)).size(),
                        ((List<?>) invocation.getArgument(1)).size()
                ));

        LeagueSeasonCycleResult result = new LeagueSeasonExpirationUseCase(
                leagueRepository, leagueSeasonRepository, clock
        ).execute();

        assertEquals(new LeagueSeasonCycleResult(1, 1), result);
        ArgumentCaptor<List<LeagueSeason>> expiredCaptor = ArgumentCaptor.forClass(List.class);
        ArgumentCaptor<List<LeagueSeason>> startedCaptor = ArgumentCaptor.forClass(List.class);
        verify(leagueSeasonRepository).applyCycle(expiredCaptor.capture(), startedCaptor.capture());
        assertEquals(expirationDate, expiredCaptor.getValue().getFirst().getEndDate());
        assertEquals("2026-10", startedCaptor.getValue().getFirst().getName());
        assertEquals(LocalDate.of(2026, 10, 1), startedCaptor.getValue().getFirst().getStartDate());
    }

    @Test
    void doesNotStartDuplicateSeasonWhenTheCycleRunsAgain() {
        Clock clock = Clock.fixed(TODAY.atStartOfDay(ZONE).toInstant(), ZONE);
        UUID leagueId = UUID.randomUUID();
        LeagueSeason currentSeason = LeagueSeason.create(leagueId, "2026-10", TODAY);
        when(leagueSeasonRepository.findActiveStartedOnOrBefore(LocalDate.of(2026, 9, 30)))
                .thenReturn(List.of());
        when(leagueRepository.findAll()).thenReturn(List.of(league(leagueId)));
        when(leagueSeasonRepository.findByLeagueId(leagueId)).thenReturn(List.of(currentSeason));
        when(leagueSeasonRepository.applyCycle(anyList(), anyList()))
                .thenReturn(new LeagueSeasonCycleResult(0, 0));

        LeagueSeasonCycleResult result = new LeagueSeasonExpirationUseCase(
                leagueRepository, leagueSeasonRepository, clock
        ).execute();

        assertEquals(new LeagueSeasonCycleResult(0, 0), result);
        verify(leagueSeasonRepository).applyCycle(List.of(), List.of());
    }

    @Test
    void doesNotOverlapAnActiveSeasonWithAnotherName() {
        Clock clock = Clock.fixed(TODAY.atStartOfDay(ZONE).toInstant(), ZONE);
        UUID leagueId = UUID.randomUUID();
        LeagueSeason activeSeason = LeagueSeason.create(leagueId, "manual-season", TODAY);
        when(leagueSeasonRepository.findActiveStartedOnOrBefore(LocalDate.of(2026, 9, 30)))
                .thenReturn(List.of());
        when(leagueRepository.findAll()).thenReturn(List.of(league(leagueId)));
        when(leagueSeasonRepository.findByLeagueId(leagueId)).thenReturn(List.of(activeSeason));
        when(leagueSeasonRepository.applyCycle(anyList(), anyList()))
                .thenReturn(new LeagueSeasonCycleResult(0, 0));

        LeagueSeasonCycleResult result = new LeagueSeasonExpirationUseCase(
                leagueRepository, leagueSeasonRepository, clock
        ).execute();

        assertEquals(new LeagueSeasonCycleResult(0, 0), result);
        verify(leagueSeasonRepository).applyCycle(List.of(), List.of());
    }

    private League league(UUID id) {
        return League.restore(id, "League", "Test league", 5, 100, 0);
    }
}
