package co.jpa.adapter;

import co.jpa.mapper.LeagueJpaMapper;
import co.jpa.repository.LeagueJpaRepository;
import co.model.league.League;
import co.model.league.gateways.LeagueRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class LeagueJpaAdapter implements LeagueRepository {

    private final LeagueJpaRepository leagueJpaRepository;

    @Override
    @Transactional
    public League save(League league) {
        return LeagueJpaMapper.toDomain(leagueJpaRepository.save(LeagueJpaMapper.toEntity(league)));
    }

    @Override
    @Transactional(readOnly = true)
    public League findById(UUID id) {
        return LeagueJpaMapper.toDomain(leagueJpaRepository.findById(id).orElse(null));
    }

    @Override
    @Transactional(readOnly = true)
    public List<League> findAll() {
        return leagueJpaRepository.findAll().stream().map(LeagueJpaMapper::toDomain).toList();
    }
}
