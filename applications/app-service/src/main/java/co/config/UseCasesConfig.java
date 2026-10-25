package co.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import java.time.Clock;
import java.time.ZoneId;

@Configuration
@ComponentScan(basePackages = "co.usecase",
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = "^.+UseCase$")
        },
        useDefaultFilters = false)
public class UseCasesConfig {

    @Bean
    public Clock applicationClock(
            @Value("${scheduler.tasks.league-season-expiration.timezone:UTC}") String timezone
    ) {
        return Clock.system(ZoneId.of(timezone));
    }
}
