package co.security.adapter;

import co.model.authentication.AuthenticatedUser;
import co.model.authentication.gateways.AuthenticationTokenRepository;
import co.security.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationTokenAdapter implements AuthenticationTokenRepository {

    private final JwtUtil jwtUtil;

    @Override
    public String generateToken(AuthenticatedUser authenticatedUser) {
        return jwtUtil.createToken(authenticatedUser.email(), authenticatedUser.userId(), authenticatedUser.role());
    }

    @Override
    public AuthenticatedUser validate(String token) {
        var claims = jwtUtil.extractClaims(jwtUtil.validateToken(token));
        return new AuthenticatedUser(claims.userId(), claims.email(), claims.role());
    }
}