package pl.kaminski.forum.users.application;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import pl.kaminski.forum.users.domain.UsernameVO;

import java.util.Date;

@RequiredArgsConstructor
public class JwtUtils {

    private static final Long TOKEN_VALIDITY = 7200000L;

    private final String secretKey;

    public String generateToken(UsernameVO usernameVO) {
        return JWT.create()
                .withSubject(usernameVO.getUsername())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_VALIDITY))
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
