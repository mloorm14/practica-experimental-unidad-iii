package ec.edu.uteq.pfcbackend.security;

import java.time.Duration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class TokenBlacklistService {

    private static final String PREFIJO = "jwt_blacklist:";

    private final StringRedisTemplate redisTemplate;

    public TokenBlacklistService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void agregar(String jti, Duration tiempoRestante) {
        if (tiempoRestante == null || tiempoRestante.isNegative() || tiempoRestante.isZero()) {
            return;
        }
        redisTemplate.opsForValue().set(PREFIJO + jti, "revocado", tiempoRestante);
    }

    public boolean estaEnBlacklist(String jti) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(PREFIJO + jti));
    }
}
