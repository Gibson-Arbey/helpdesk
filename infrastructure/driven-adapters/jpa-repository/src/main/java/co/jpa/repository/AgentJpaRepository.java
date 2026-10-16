package co.jpa.repository;

import co.jpa.entity.AgentJpaEntity;
import co.model.agentprofile.AgentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AgentJpaRepository extends JpaRepository<AgentJpaEntity, Long> {

    @Query("SELECT a FROM AgentJpaEntity a WHERE a.status = :status")
    Optional<AgentJpaEntity> findOneByStatus(AgentStatus status);
}
