package co.model.agentleaguestanding;

import co.model.exception.InvalidFieldException;
import lombok.Getter;

import java.util.UUID;

@Getter
public class AgentLeagueStanding {

    private final UUID id;
    private final UUID agentProfileId;
    private final UUID leagueSeasonId;
    private final int points;
    private final Integer position;

    private AgentLeagueStanding(UUID id, UUID agentProfileId, UUID leagueSeasonId, int points, Integer position) {
        if (id == null) throw new InvalidFieldException("Standing id cannot be null");
        if (agentProfileId == null) throw new InvalidFieldException("Agent profile id cannot be null");
        if (leagueSeasonId == null) throw new InvalidFieldException("League season id cannot be null");
        if (points < 0) throw new InvalidFieldException("Standing points cannot be negative");
        if (position != null && position < 1) throw new InvalidFieldException("Standing position must be positive");

        this.id = id;
        this.agentProfileId = agentProfileId;
        this.leagueSeasonId = leagueSeasonId;
        this.points = points;
        this.position = position;
    }

    public static AgentLeagueStanding create(UUID agentProfileId, UUID leagueSeasonId) {
        return new AgentLeagueStanding(UUID.randomUUID(), agentProfileId, leagueSeasonId, 0, null);
    }

    public static AgentLeagueStanding restore(UUID id, UUID agentProfileId, UUID leagueSeasonId, int points, Integer position) {
        return new AgentLeagueStanding(id, agentProfileId, leagueSeasonId, points, position);
    }

    public AgentLeagueStanding addPoints(int pointsToAdd) {
        if (pointsToAdd < 1) throw new InvalidFieldException("Points added must be positive");
        try {
            return new AgentLeagueStanding(id, agentProfileId, leagueSeasonId, Math.addExact(points, pointsToAdd), position);
        } catch (ArithmeticException overflow) {
            throw new InvalidFieldException("Standing points exceed the supported range");
        }
    }

    public AgentLeagueStanding assignPosition(Integer newPosition) {
        return new AgentLeagueStanding(id, agentProfileId, leagueSeasonId, points, newPosition);
    }
}
