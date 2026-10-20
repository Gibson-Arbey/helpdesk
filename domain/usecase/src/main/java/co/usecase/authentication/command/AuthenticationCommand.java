package co.usecase.authentication.command;

public record AuthenticationCommand(
    String email,
    String password
) {
}
