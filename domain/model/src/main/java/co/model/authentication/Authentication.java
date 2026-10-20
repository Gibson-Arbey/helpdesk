package co.model.authentication;

import lombok.Getter;

import java.util.UUID;

@Getter
public class Authentication {

    private final UUID userId;
    private final String name;
    private final String email;
    private final String role;
    private final String token;

    private Authentication(UUID userId, String name, String email, String role, String token) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.role = role;
        this.token = token;
    }

    public static Authentication create(UUID userId, String name, String email, String role, String token) {
        return new Authentication(userId, name, email, role, token);
    }
}
