package co.jpa.repository;

import co.jpa.entity.TicketJpaEntity;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TicketJpaRepository extends JpaRepository<TicketJpaEntity, Long> {

    @Override
    @EntityGraph(attributePaths = {"customer", "agent"})
    Optional<TicketJpaEntity> findById(Long id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT t FROM TicketJpaEntity t WHERE t.id = :id")
    Optional<TicketJpaEntity> findByIdForUpdate(@Param("id") Long id);

    @Override
    @EntityGraph(attributePaths = {"customer", "agent"})
    List<TicketJpaEntity> findAll();

    @EntityGraph(attributePaths = {"customer", "agent"})
    List<TicketJpaEntity> findByAgent_Id(UUID agentId);

    @EntityGraph(attributePaths = {"customer", "agent"})
    List<TicketJpaEntity> findByCustomer_Id(UUID customerId);
}
