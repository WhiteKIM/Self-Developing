package whitekim.self_developing.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import whitekim.self_developing.exception.PermissionDeniedException;
import whitekim.self_developing.jwt.redis.RedisToken;
import whitekim.self_developing.jwt.redis.RedisTokenRepository;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Optional;

@Component
@Slf4j
public class JwtUtils {
    private final SecretKey secretKey; // 시크릿 키
    private final String key;
    private final Long refreshExpirationTime; // 리프레시토큰 만료시간
    private final Long accessExpirationTime; // 엑세스토큰 만료시간
    private final RedisTokenRepository redisTokenRepository;
    
    public JwtUtils(@Value("${secret.key}") String key, @Value("${token.refresh-token.expiration}") Long refreshExpirationTime, @Value("${token.access-token.expiration}") Long accessExpirationTime, RedisTokenRepository redisTokenRepository) {
        this.refreshExpirationTime = refreshExpirationTime;
        this.accessExpirationTime = accessExpirationTime;
        this.key = key;
        this.secretKey = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
        this.redisTokenRepository = redisTokenRepository;
    }

    /**
     * 토큰 발행을 수행
     * @param username - 로그인한 사용자
     */
    public void publishToken(String username, HttpServletResponse response) {
        String refreshToken = publishRefreshToken(username);

        String accessToken = publishAccessToken(username);

        redisTokenRepository.save(new RedisToken(username, accessToken, refreshToken));
        
        // 클라이언트에는 엑세스 토큰만 전달
        response.setHeader("Access-Token", accessToken);
    }

    public String getUsername(String accessToken) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(accessToken)
                .getPayload()
                .getSubject();
    }

    /**
     * 엑세스토큰이 만료되면 새로운 엑세스토큰 재발행
     *
     * @param accessToken - 엑세스토큰
     * @param response - 응답
     */
    public String republishAccessToken(String accessToken, HttpServletResponse response) {
        Optional<RedisToken> optionalRedisToken = redisTokenRepository.findByAccessToken(accessToken);

        if(optionalRedisToken.isEmpty()) {
            throw new PermissionDeniedException("잘못된 인증 요청입니다.");
        }

        RedisToken redisToken = optionalRedisToken.get();
        String refreshToken = redisToken.getRefreshToken();

        // 이전에 발행된 리프레시 토큰이 존재하고, 해당 토큰이 유효한 경우
        if(!verifyRefreshToken(refreshToken)) {
            throw new RuntimeException("만료된 인증정보입니다.");
        }

        String username = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(refreshToken)
                .getPayload()
                .getSubject();

        // 토큰 재발행 후 업데이트
        String newAccessToken = publishAccessToken(username);
        redisToken.updateAccessToken(newAccessToken);
        redisTokenRepository.save(redisToken);

        response.setHeader("Access-Token", newAccessToken);
        
        // 해당 사용자명 반환
        return username;
    }

    /**
     * 엑세스 토근 발행
     */
    private String publishAccessToken(String username) {
        return Jwts.builder()
                .subject(username)
                .signWith(secretKey)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + accessExpirationTime))
                .compact();
    }

    /**
     * 리프레시 토근 발행
     */
    private String publishRefreshToken(String username) {
        return Jwts.builder()
                .subject(username)
                .signWith(secretKey)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + refreshExpirationTime))
                .compact();
    }

    /**
     * 엑세스 토큰 검증
     */
    public boolean verifyAccessToken(String accessToken) {
        Jws<Claims> claimsJws = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(accessToken);

        // 만료된 토큰
        return claimsJws.getPayload().getExpiration().before(new Date());
    }


    /**
     * 리프레시 토큰 검증
     */
    public boolean verifyRefreshToken(String refreshToken) {
        Jws<Claims> claimsJws = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(refreshToken);

        // 만료된 토큰
        return claimsJws.getPayload().getExpiration().before(new Date());
    }

    /**
     * 특정 사용자 토큰 제거
     * 본인이 아닌 제3자의 요청만으로는 제거하지 않는다.
     * @param username     : 토큰을 제거할 사용자명
     */
    public void removeMemberToken(String username) {
        Optional<RedisToken> optToken = redisTokenRepository.findById(username);

        if(optToken.isEmpty()) {
            throw new PermissionDeniedException("권한이 없는 접근입니다.");
        }

        RedisToken token = optToken.get();

        if(verifyRefreshToken(token.getRefreshToken()) || verifyAccessToken(token.getAccessToken())) {
            redisTokenRepository.deleteById(username);
            return;
        }

        throw new PermissionDeniedException("권한이 없는 접근입니다.");
    }


}
