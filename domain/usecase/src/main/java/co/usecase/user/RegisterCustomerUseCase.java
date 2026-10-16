package co.usecase.user;

import co.model.user.User;
import co.model.user.UserRole;
import co.usecase.user.command.CreateUserCommand;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RegisterCustomerUseCase {

    private final CreateUserUseCase createUserUseCase;

    public User execute(CreateUserCommand command) {
        return createUserUseCase.execute(command, UserRole.CUSTOMER);
    }
}
