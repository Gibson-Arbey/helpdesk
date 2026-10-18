package co.model.user;

import co.model.user.valueobject.Email;
import co.model.user.valueobject.Password;
import lombok.Getter;

import java.util.UUID;

@Getter
public class User {

    private final UUID id;
    private final String name;
    private final Email email;
    private final Password password;
    private final UserRole role;

    private User(UUID id, String name, Email email, Password password, UserRole role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public static User create(String name, Email email, Password password, UserRole role) {
        return new User(UUID.randomUUID(), name, email, password, role);
    }

    public static User restore(UUID id, String name, Email email, Password password, UserRole role) {
        return new User(id, name, email, password, role);
    }
}
