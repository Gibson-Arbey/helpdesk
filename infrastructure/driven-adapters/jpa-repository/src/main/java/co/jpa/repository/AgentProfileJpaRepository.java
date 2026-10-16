package co.jpa.repository;

import co.jpa.entity.AgentProfileJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface AgentProfileJpaRepository extends JpaRepository<AgentProfileJpaEntity, Long> {

    @Query("SELECT a FROM AgentProfileJpaEntity a WHERE a.user.id = :userId")
    AgentProfileJpaEntity findByUserId(UUID userId);

}
