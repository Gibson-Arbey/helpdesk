package co.api.authentication.mapper;

import co.api.authentication.request.AuthenticationRequest;
import co.usecase.authentication.command.AuthenticationCommand;

public class AuthenticationRequestMapper {

    public static AuthenticationCommand toAuthenticationCommand(AuthenticationRequest request) {
        if (request == null) {
            return null;
        }
        return new AuthenticationCommand(request.email(), request.password());
    }
}
