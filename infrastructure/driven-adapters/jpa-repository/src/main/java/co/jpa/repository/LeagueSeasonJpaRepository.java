package co.jpa.repository;

import co.jpa.entity.LeagueSeasonJpaEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface LeagueSeasonJpaRepository extends JpaRepository<LeagueSeasonJpaEntity, UUID> {

    @EntityGraph(attributePaths = "league")
    List<LeagueSeasonJpaEntity> findByEndDateIsNullAndStartDateLessThanEqual(LocalDate cutoffDate);

    @EntityGraph(attributePaths = "league")
    List<LeagueSeasonJpaEntity> findByLeague_IdOrderByStartDateDesc(UUID leagueId);

    @Modifying(flushAutomatically = true)
    @Query(value = """
            INSERT INTO leagueseasons (lese_id, leag_id, lese_name, lese_startdate, lese_enddate)
            VALUES (:id, :leagueId, :name, :startDate, NULL)
            ON CONFLICT (leag_id, lese_name) DO NOTHING
            """, nativeQuery = true)
    int insertIfAbsent(
            @Param("id") UUID id,
            @Param("leagueId") UUID leagueId,
            @Param("name") String name,
            @Param("startDate") LocalDate startDate
    );
}
