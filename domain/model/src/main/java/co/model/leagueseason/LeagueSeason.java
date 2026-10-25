package co.model.leagueseason;

import co.model.exception.InvalidFieldException;
import lombok.Getter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
public class LeagueSeason {

    private final UUID id;
    private final UUID leagueId;
    private final String name;
    private final LocalDate startDate;
    private final LocalDate endDate;

    private LeagueSeason(UUID id, UUID leagueId, String name, LocalDate startDate, LocalDate endDate) {
        if (id == null) throw new InvalidFieldException("League season id cannot be null");
        if (leagueId == null) throw new InvalidFieldException("League id cannot be null");
        if (name == null || name.isBlank()) throw new InvalidFieldException("League season name cannot be blank");
        if (startDate == null) throw new InvalidFieldException("League season start date cannot be null");
        if (endDate != null && endDate.isBefore(startDate)) {
            throw new InvalidFieldException("League season end date cannot precede its start date");
        }

        this.id = id;
        this.leagueId = leagueId;
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static LeagueSeason create(UUID leagueId, String name, LocalDate startDate) {
        return new LeagueSeason(UUID.randomUUID(), leagueId, name, startDate, null);
    }

    public static LeagueSeason restore(UUID id, UUID leagueId, String name, LocalDate startDate, LocalDate endDate) {
        return new LeagueSeason(id, leagueId, name, startDate, endDate);
    }

    public boolean isActive() {
        return endDate == null;
    }

    public LeagueSeason expire(LocalDate expirationDate) {
        if (!isActive()) {
            throw new InvalidFieldException("League season is already expired");
        }
        if (expirationDate == null || expirationDate.isBefore(startDate)) {
            throw new InvalidFieldException("Expiration date must be on or after the start date");
        }
        return new LeagueSeason(id, leagueId, name, startDate, expirationDate);
    }
}
