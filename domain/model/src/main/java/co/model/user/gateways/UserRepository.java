package co.model.user.gateways;

import co.model.user.User;

import java.util.UUID;

public interface UserRepository {

    User save(User user);

    User findByEmail(String email);

    User findById(UUID id);

    boolean existsByEmail(String email);
}
