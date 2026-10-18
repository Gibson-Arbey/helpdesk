package co.api.user.request;

import jakarta.validation.constraints.NotNull;

public record RegisterUserRequest(
    @NotNull(message = "Name is required") String name,
    @NotNull(message = "Email is required") String email,
    @NotNull(message = "Password is required") String password
) {
}
