package co.usecase.user;

import co.model.user.User;
import co.model.user.UserRole;
import co.model.exception.InvalidFieldException;
import co.model.user.exception.EmailAlreadyExistsException;
import co.model.user.gateways.PasswordEncoderRepository;
import co.model.user.gateways.UserRepository;
import co.model.user.valueobject.Email;
import co.model.user.valueobject.Password;
import co.usecase.user.command.CreateUserCommand;
import co.usecase.user.policy.PasswordPolicy;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreateUserUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoderRepository passwordEncoderRepository;

    public User execute(CreateUserCommand command, UserRole role) {
        if (command == null || command.name() == null || command.name().isBlank()) {
            throw new InvalidFieldException("User name cannot be blank");
        }
        if (role == null) {
            throw new InvalidFieldException("User role cannot be null");
        }
        Email email = new Email(command.email());
        if (userRepository.existsByEmail(command.email())) {
            throw new EmailAlreadyExistsException("User with email " + command.email() + " already exists");
        }
        PasswordPolicy.validate(command.password());
        Password password = new Password(passwordEncoderRepository.encode(command.password()));
        User user = User.create(command.name(), email, password, role);
        return userRepository.save(user);
    }


}
