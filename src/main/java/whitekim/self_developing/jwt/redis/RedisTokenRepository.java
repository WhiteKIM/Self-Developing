package whitekim.self_developing.jwt.redis;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RedisTokenRepository extends CrudRepository<RedisToken, String> {
    Optional<RedisToken> findByAccessToken(String accessToken);
    Optional<RedisToken> findByUsername(String username);
    Optional<RedisToken> findByRefreshToken(String refreshToken);
}
