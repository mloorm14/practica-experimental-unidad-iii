package ec.edu.uteq.pfcbackend.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtService {

    private final SecretKey secretKey;
    private final long expirationMinutes;

    public JwtService(@Value("${jwt.secret}") String secret,
                       @Value("${jwt.expiration-minutes}") long expirationMinutes) {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret));
        this.expirationMinutes = expirationMinutes;
    }

    public String generarToken(String username, String rol) {
        Instant ahora = Instant.now();
        Instant expiracion = ahora.plus(Duration.ofMinutes(expirationMinutes));

        return Jwts.builder()
                .subject(username)
                .id(UUID.randomUUID().toString())
                .claim("rol", rol)
                .issuedAt(Date.from(ahora))
                .expiration(Date.from(expiracion))
                .signWith(secretKey)
                .compact();
    }

    public Claims validarYExtraerClaims(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public long getExpirationMinutes() {
        return expirationMinutes;
    }
}
