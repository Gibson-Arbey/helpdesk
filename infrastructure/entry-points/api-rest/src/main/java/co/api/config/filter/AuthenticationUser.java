package co.api.config.filter;

import java.util.UUID;

public record AuthenticationUser(UUID userId, String email, String role) {
}
