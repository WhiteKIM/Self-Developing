package whitekim.self_developing.jwt.redis;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@AllArgsConstructor
@Getter
@RedisHash(value = "token", timeToLive =  1000 * 60 * 24 * 15)
public class RedisToken {
    @Id
    private String id;

    @Indexed
    private String accessToken;

    private String refreshToken;

    public void updateAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
