package co.model.authentication;

import lombok.Getter;

@Getter
public class Authentication {

    private final Long userId;
    private final String username;
    private final String email;
    private final String role;
    private final String token;

    private Authentication(Long userId, String username, String email, String role, String token) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.role = role;
        this.token = token;
    }

    public static Authentication create(Long userId, String username, String email, String role, String token) {
        return new Authentication(userId, username, email, role, token);
    }
}
