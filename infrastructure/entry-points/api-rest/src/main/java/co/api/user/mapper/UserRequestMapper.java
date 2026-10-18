package co.api.user.mapper;

import co.api.user.request.RegisterUserRequest;
import co.usecase.user.command.CreateUserCommand;

public class UserRequestMapper {

    public static CreateUserCommand toCreateUserCommand(RegisterUserRequest request) {
        if (request == null) {
            return null;
        }
        return new CreateUserCommand(request.name(), request.email(), request.password());
    }
}
