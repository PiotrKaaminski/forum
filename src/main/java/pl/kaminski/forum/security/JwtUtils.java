package pl.kaminski.forum.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

public class JwtUtils {

    @Value( "${jwt.secret-key}")
    private String secretKey;

    public String generateToken(String username) {
        return JWT.create()
                .withSubject(username)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 7200000L))
                .sign(Algorithm.HMAC256(secretKey));
    }

    public String extractUsername(String token) {
        return getDecodedJWT(token).getSubject();
    }

    public boolean isTokenValid(String token) {
        try {
            DecodedJWT jwt = JWT.require(Algorithm.HMAC256(secretKey))
                    .build()
                    .verify(token);

            return !jwt.getExpiresAt().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    private DecodedJWT getDecodedJWT(String token) {
        try {
            return JWT.require(Algorithm.HMAC256(secretKey)).build().verify(token);
        } catch (JWTVerificationException e) {
            throw new RuntimeException("invalid JWT token", e);
        }
    }
}
