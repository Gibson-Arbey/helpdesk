package co.usecase.authentication;

import co.model.authentication.AuthenticatedUser;
import co.model.authentication.Authentication;
import co.model.authentication.gateways.AuthenticationRepository;
import co.model.authentication.gateways.AuthenticationTokenRepository;
import co.model.user.User;
import co.model.user.exception.UserNotFoundException;
import co.model.user.gateways.UserRepository;
import co.usecase.authentication.command.AuthenticationCommand;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthenticationUseCase {

    private final AuthenticationRepository authenticationRepository;
    private final AuthenticationTokenRepository authenticationTokenRepository;
    private final UserRepository userRepository;

    public Authentication execute(AuthenticationCommand command) {
        String email = command.email();
        String password = command.password();

        // 1. Validar las credenciales
        authenticationRepository.authenticate(email, password);

        // 2. Buscar el usuario
        User user = userRepository.findByEmail(email);
        if(user == null) {
            throw new UserNotFoundException("User with email " + email + " does not exist");
        }

        String role = user.getRole().toString();

        // 4. Generar token
        AuthenticatedUser authenticatedUser = new AuthenticatedUser(
                user.getId(),
                user.getEmail().value(),
                role
        );
        String token = authenticationTokenRepository.generateToken(authenticatedUser);

        // 5. Construir respuesta
        return Authentication.create(
                user.getId(),
                user.getName(),
                user.getEmail().value(),
                role,
                token
        );
    }
}
