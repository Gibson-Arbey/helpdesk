package co.model.user.gateways;

import co.model.user.User;

public interface UserRepository {

    User save(User user);

    User findByEmail(String email);

    boolean existsByEmail(String email);
}
