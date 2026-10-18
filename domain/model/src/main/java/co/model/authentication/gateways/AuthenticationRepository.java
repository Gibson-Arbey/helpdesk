package co.model.authentication.gateways;

public interface AuthenticationRepository {

    void authenticate(String email, String password);
}
