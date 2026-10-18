package co.model.authentication;

import java.util.UUID;

public record AuthenticatedUser(UUID userId, String email, String role) {
}
