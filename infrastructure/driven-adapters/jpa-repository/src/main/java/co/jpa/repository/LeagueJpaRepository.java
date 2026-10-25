package co.jpa.repository;

import co.jpa.entity.LeagueJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LeagueJpaRepository extends JpaRepository<LeagueJpaEntity, UUID> {
}
