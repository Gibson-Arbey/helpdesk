package co.model.leagueseason;

import co.model.exception.InvalidFieldException;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LeagueSeasonTest {

    @Test
    void activeSeasonCanExpireOnOrAfterItsStartDate() {
        UUID leagueId = UUID.randomUUID();
        LocalDate start = LocalDate.of(2026, 9, 1);
        LeagueSeason active = LeagueSeason.create(leagueId, "September", start);

        LeagueSeason expired = active.expire(LocalDate.of(2026, 9, 30));

        assertTrue(active.isActive());
        assertFalse(expired.isActive());
        assertEquals(leagueId, expired.getLeagueId());
        assertEquals(start, expired.getStartDate());
        assertEquals(LocalDate.of(2026, 9, 30), expired.getEndDate());
    }

    @Test
    void validatesSeasonIdentityDatesAndRepeatExpiration() {
        LocalDate start = LocalDate.of(2026, 9, 1);
        assertThrows(InvalidFieldException.class, () -> LeagueSeason.create(null, "September", start));
        assertThrows(InvalidFieldException.class, () -> LeagueSeason.create(UUID.randomUUID(), " ", start));
        assertThrows(InvalidFieldException.class, () -> LeagueSeason.create(UUID.randomUUID(), "September", null));
        assertThrows(InvalidFieldException.class,
                () -> LeagueSeason.create(UUID.randomUUID(), "September", start).expire(start.minusDays(1)));
        assertThrows(InvalidFieldException.class,
                () -> LeagueSeason.create(UUID.randomUUID(), "September", start).expire(start).expire(start));
        assertThrows(InvalidFieldException.class,
                () -> LeagueSeason.restore(UUID.randomUUID(), UUID.randomUUID(), "September", start, start.minusDays(1)));
    }
}
