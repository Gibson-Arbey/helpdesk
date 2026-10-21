package co.usecase.leagueseason;

import co.model.leagueseason.gateways.LeagueSeasonRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LeagueSeasonExpirationUseCase {

    private final LeagueSeasonRepository leagueSeasonRepository;

    public void execute() {

    }
}
