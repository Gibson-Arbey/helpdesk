package co.api.authentication.mapper;

import co.api.authentication.response.AuthenticationResponse;

public class AuthenticationResponseMapper {

    public static AuthenticationResponse toResponse(co.model.authentication.Authentication authentication) {
        if (authentication == null) {
            return  null;
        }
        return new AuthenticationResponse(
                authentication.getToken(),
                new co.api.authentication.response.AuthenticationUserResponse(
                        authentication.getUserId(),
                        authentication.getName(),
                        authentication.getEmail(),
                        authentication.getRole()
                )
        );
    }
}
