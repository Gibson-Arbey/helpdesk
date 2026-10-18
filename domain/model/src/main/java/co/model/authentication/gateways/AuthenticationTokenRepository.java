package co.model.authentication.gateways;

import co.model.authentication.AuthenticatedUser;

public interface AuthenticationTokenRepository {

    String generateToken(AuthenticatedUser authenticatedUser);

    AuthenticatedUser validate(String token);

}
