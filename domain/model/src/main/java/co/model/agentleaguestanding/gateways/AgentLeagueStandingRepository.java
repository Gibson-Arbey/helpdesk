package co.model.agentleaguestanding.gateways;

import co.model.agentleaguestanding.AgentLeagueStanding;

import java.util.List;
import java.util.UUID;

public interface AgentLeagueStandingRepository {

    AgentLeagueStanding save(AgentLeagueStanding standing);

    AgentLeagueStanding findById(UUID id);

    AgentLeagueStanding findByAgentProfileIdAndLeagueSeasonId(UUID agentProfileId, UUID leagueSeasonId);

    List<AgentLeagueStanding> findByLeagueSeasonId(UUID leagueSeasonId);
}
