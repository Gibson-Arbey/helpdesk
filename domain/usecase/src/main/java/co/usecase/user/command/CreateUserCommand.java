package co.usecase.user.command;

public record CreateUserCommand(
    String name,
    String email,
    String password) {
}
