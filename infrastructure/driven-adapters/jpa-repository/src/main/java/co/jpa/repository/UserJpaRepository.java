package co.jpa.repository;

import co.jpa.entity.UserJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<UserJpaEntity, Long> {

    UserJpaEntity findByEmail(String email);

    boolean existsByEmail(String email);
}
