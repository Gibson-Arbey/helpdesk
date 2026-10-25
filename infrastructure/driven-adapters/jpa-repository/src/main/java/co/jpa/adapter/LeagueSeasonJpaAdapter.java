package co.jpa.adapter;

import co.jpa.mapper.LeagueSeasonJpaMapper;
import co.jpa.repository.LeagueSeasonJpaRepository;
import co.model.leagueseason.LeagueSeason;
import co.model.leagueseason.LeagueSeasonCycleResult;
import co.model.leagueseason.gateways.LeagueSeasonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class LeagueSeasonJpaAdapter implements LeagueSeasonRepository {

    private final LeagueSeasonJpaRepository leagueSeasonJpaRepository;

    @Override
    @Transactional
    public LeagueSeason save(LeagueSeason leagueSeason) {
        return LeagueSeasonJpaMapper.toDomain(
                leagueSeasonJpaRepository.save(LeagueSeasonJpaMapper.toEntity(leagueSeason))
        );
    }

    @Override
    @Transactional
    public List<LeagueSeason> saveAll(List<LeagueSeason> leagueSeasons) {
        return leagueSeasonJpaRepository.saveAll(
                        leagueSeasons.stream().map(LeagueSeasonJpaMapper::toEntity).toList()
                ).stream()
                .map(LeagueSeasonJpaMapper::toDomain)
                .toList();
    }

    @Override
    @Transactional
    public LeagueSeasonCycleResult applyCycle(
            List<LeagueSeason> seasonsToExpire,
            List<LeagueSeason> seasonsToStart
    ) {
        int expiredCount = leagueSeasonJpaRepository.saveAll(
                seasonsToExpire.stream().map(LeagueSeasonJpaMapper::toEntity).toList()
        ).size();
        int startedCount = seasonsToStart.stream()
                .mapToInt(season -> leagueSeasonJpaRepository.insertIfAbsent(
                        season.getId(),
                        season.getLeagueId(),
                        season.getName(),
                        season.getStartDate()
                ))
                .sum();
        return new LeagueSeasonCycleResult(expiredCount, startedCount);
    }

    @Override
    @Transactional(readOnly = true)
    public LeagueSeason findById(UUID id) {
        return LeagueSeasonJpaMapper.toDomain(leagueSeasonJpaRepository.findById(id).orElse(null));
    }

    @Override
    @Transactional(readOnly = true)
    public List<LeagueSeason> findByLeagueId(UUID leagueId) {
        return leagueSeasonJpaRepository.findByLeague_IdOrderByStartDateDesc(leagueId).stream()
                .map(LeagueSeasonJpaMapper::toDomain)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<LeagueSeason> findActiveStartedOnOrBefore(LocalDate cutoffDate) {
        return leagueSeasonJpaRepository.findByEndDateIsNullAndStartDateLessThanEqual(cutoffDate).stream()
                .map(LeagueSeasonJpaMapper::toDomain)
                .toList();
    }
}
