package co.jpa.adapter;

import co.jpa.mapper.UserJpaMapper;
import co.jpa.repository.UserJpaRepository;
import co.model.user.User;
import co.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UserJpaAdapter implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    @Override
    public User save(User user) {
        return UserJpaMapper
                .toDomain(userJpaRepository
                        .save(UserJpaMapper.toEntity(user)));
    }

    @Override
    public User findByEmail(String email) {
        return UserJpaMapper
                .toDomain(userJpaRepository
                        .findByEmail(email));
    }

    @Override
    public User findById(UUID id) {
        return UserJpaMapper
                .toDomain(userJpaRepository
                        .findById(id)
                        .orElse(null));
    }

    @Override
    public boolean existsByEmail(String email) {
        return userJpaRepository.existsByEmail(email);
    }
}
