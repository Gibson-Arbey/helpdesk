package co.jpa.repository;

import co.jpa.entity.UserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserJpaRepository extends JpaRepository<UserJpaEntity, UUID> {

    UserJpaEntity findByEmail(String email);

    boolean existsByEmail(String email);
}
