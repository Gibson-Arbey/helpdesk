package co.usecase.user;

import co.model.user.User;
import co.model.user.UserRole;
import co.model.user.exception.EmailAlreadyExistsException;
import co.model.user.gateways.UserRepository;
import co.usecase.user.command.CreateUserCommand;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateUserUseCase {

    private final UserRepository userRepository;

    public User execute(CreateUserCommand command, UserRole role) {
        if (userRepository.existsByEmail(command.email())) {
            throw new EmailAlreadyExistsException("User with email " + command.email() + " already exists");
        }
        User user = User.create(command.name(), command.email(), command.password(), role);
        return userRepository.save(user);
    }


}
