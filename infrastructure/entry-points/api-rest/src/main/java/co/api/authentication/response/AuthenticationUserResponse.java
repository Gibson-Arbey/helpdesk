package co.api.authentication.response;

import java.util.UUID;

public record AuthenticationUserResponse(UUID id, String name, String email, String role) {
}
