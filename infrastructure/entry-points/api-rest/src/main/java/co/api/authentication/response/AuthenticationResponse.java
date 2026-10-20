package co.api.authentication.response;

public record AuthenticationResponse(String token, AuthenticationUserResponse user) {
}
