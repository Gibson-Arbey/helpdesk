package co.security.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Locale;
import java.util.UUID;

@Component
public class JwtUtil {

    private final SecurityConstant securityConstant;
    private final Algorithm algorithm;

    public JwtUtil(SecurityConstant securityConstant) {
        this.securityConstant = securityConstant;
        this.algorithm = Algorithm.HMAC256(securityConstant.getJwtKeyPrivate());
    }

    public String createToken(String email, UUID userId, String role) {
        return JWT.create()
                .withIssuer(securityConstant.getJwtUserGenerator())
                .withSubject(email)
                .withClaim("userId", userId.toString())
                .withClaim("role", normalizeRole(role))
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + securityConstant.getJwtExpiration()))
                .sign(algorithm);
    }

    public com.auth0.jwt.interfaces.DecodedJWT validateToken(String token) {
        return JWT.require(algorithm)
                .withIssuer(securityConstant.getJwtUserGenerator())
                .build()
                .verify(token);
    }

    public JwtClaims extractClaims(com.auth0.jwt.interfaces.DecodedJWT jwt) {
        UUID userId = jwt.getClaim("userId").as(UUID.class);
        String email = jwt.getSubject();
        String role = jwt.getClaim("role").asString();

        if (userId == null || email == null || email.isBlank() || role == null || role.isBlank()) {
            throw new IllegalArgumentException("JWT is missing required identity claims");
        }

        return new JwtClaims(userId, email, normalizeRole(role));
    }

    private String normalizeRole(String role) {
        String normalizedRole = role.trim().toUpperCase(Locale.ROOT);
        return normalizedRole.startsWith("ROLE_")
                ? normalizedRole
                : "ROLE_" + normalizedRole;
    }

    public record JwtClaims(UUID userId, String email, String role) {
    }

}
