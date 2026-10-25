package co.model.agentleaguestanding;

import co.model.exception.InvalidFieldException;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AgentLeagueStandingTest {

    @Test
    void startsWithZeroPointsAndSupportsPointsAndPositionUpdates() {
        AgentLeagueStanding standing = AgentLeagueStanding.create(UUID.randomUUID(), UUID.randomUUID());

        AgentLeagueStanding ranked = standing.addPoints(25).assignPosition(2);

        assertEquals(0, standing.getPoints());
        assertNull(standing.getPosition());
        assertEquals(25, ranked.getPoints());
        assertEquals(2, ranked.getPosition());
    }

    @Test
    void enforcesTheTableConstraintsForPointsAndPositions() {
        UUID profileId = UUID.randomUUID();
        UUID seasonId = UUID.randomUUID();
        AgentLeagueStanding standing = AgentLeagueStanding.create(profileId, seasonId);

        assertThrows(InvalidFieldException.class, () -> AgentLeagueStanding.create(null, seasonId));
        assertThrows(InvalidFieldException.class, () -> standing.addPoints(0));
        assertThrows(InvalidFieldException.class, () -> standing.assignPosition(0));
        assertThrows(InvalidFieldException.class,
                () -> AgentLeagueStanding.restore(UUID.randomUUID(), profileId, seasonId, -1, null));
        assertThrows(InvalidFieldException.class,
                () -> AgentLeagueStanding.restore(UUID.randomUUID(), profileId, seasonId, 0, -1));
    }
}
