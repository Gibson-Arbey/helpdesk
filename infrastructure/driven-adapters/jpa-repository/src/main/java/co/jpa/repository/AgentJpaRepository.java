package co.jpa.repository;

import co.jpa.entity.AgentJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgentJpaRepository extends JpaRepository<AgentJpaEntity, Long> {
}
