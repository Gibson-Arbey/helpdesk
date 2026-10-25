package co.scheduler.leagueseason;

import co.scheduler.config.SchedulerProperties;
import co.usecase.leagueseason.LeagueSeasonExpirationUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class LeagueSeasonScheduler {

    private final SchedulerProperties properties;
    private final LeagueSeasonExpirationUseCase leagueSeasonExpirationUseCase;

    @Scheduled(
            cron = "${scheduler.tasks.league-season-expiration.cron}",
            zone = "${scheduler.tasks.league-season-expiration.timezone}"
    )
    public void execute() {
        if (!properties.tasks()
                .leagueSeasonExpiration().enabled()) {
            log.info("League season expiration task is disabled. Skipping execution.");
            return;
        }

        var cycleResult = leagueSeasonExpirationUseCase.execute();
        log.info("Expired {} league seasons and started {} new league seasons",
                cycleResult.expiredCount(), cycleResult.startedCount());
    }
}
