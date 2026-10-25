package co.jpa.repository;

import co.jpa.entity.AgentProfileJpaEntity;
import co.model.agentprofile.AgentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AgentProfileJpaRepository extends JpaRepository<AgentProfileJpaEntity, UUID> {

    @Query("SELECT a FROM AgentProfileJpaEntity a WHERE a.user.id = :userId")
    Optional<AgentProfileJpaEntity> findByUserId(@Param("userId") UUID userId);

    Optional<AgentProfileJpaEntity> findFirstByStatusOrderByIdAsc(AgentStatus status);

    @Lock(jakarta.persistence.LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT a FROM AgentProfileJpaEntity a WHERE a.id = :id")
    Optional<AgentProfileJpaEntity> findByIdForUpdate(@Param("id") UUID id);

    List<AgentProfileJpaEntity> findByStatusOrderByIdAsc(AgentStatus status);
}
