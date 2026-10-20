package co.usecase.authentication;

import co.model.authentication.AuthenticatedUser;
import co.model.authentication.Authentication;
import co.model.authentication.gateways.AuthenticationTokenRepository;
import co.model.user.User;
import co.model.user.exception.UserNotFoundException;
import co.model.user.gateways.UserRepository;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@RequiredArgsConstructor
public class RefreshAuthenticationUseCase {

    private final AuthenticationTokenRepository authenticationTokenRepository;
    private final UserRepository userRepository;

    public Authentication execute(UUID id){
        // 2. Buscar el usuario
        User user = userRepository.findById(id);
        if(user == null) {
            throw new UserNotFoundException("User does not exist");
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
