package co.scheduler.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "scheduler")
public record SchedulerProperties(
        Tasks tasks
) {
    public record Tasks(
            LeagueSeasonExpiration leagueSeasonExpiration
    ) {}

    public record LeagueSeasonExpiration(
            String cron,
            boolean enabled,
            String timezone
    ) {}
}
