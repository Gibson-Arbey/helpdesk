package co.jpa.repository;

import co.jpa.entity.TicketJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketJpaRepository extends JpaRepository<TicketJpaEntity, Long> {
}
