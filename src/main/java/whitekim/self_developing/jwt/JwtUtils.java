package whitekim.self_developing.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import whitekim.self_developing.exception.ExpiredTokenException;
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
    private final Long refreshExpirationTime; // 리프레시토큰 만료시간
    private final Long accessExpirationTime; // 엑세스토큰 만료시간
    private final RedisTokenRepository redisTokenRepository;

    public JwtUtils(@Value("${secret.key}") String key, @Value("${token.refresh-token.expiration}") Long refreshExpirationTime, @Value("${token.access-token.expiration}") Long accessExpirationTime, RedisTokenRepository redisTokenRepository) {
        this.refreshExpirationTime = refreshExpirationTime;
        this.accessExpirationTime = accessExpirationTime;
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
        RedisToken redisToken = null;

        String username = getUsernameByToken(accessToken);

        redisToken = optionalRedisToken.orElseGet(() -> new RedisToken(username, null, null));
        String refreshToken = redisToken.getRefreshToken();

        log.info("[JwtUtils] : {}, {}", refreshToken, accessToken);

        if(refreshToken == null) {
            // 토큰 최초 발행 시작
            refreshToken = publishRefreshToken(username);
        } else if(!verifyRefreshToken(refreshToken)) {
            // 이전에 발행된 리프레시 토큰이 존재하지만 만료된 경우
            redisTokenRepository.deleteById(username);
            throw new ExpiredTokenException("만료된 로그인 정보입니다.");
        }

        // 토큰 재발행 후 업데이트
        String newAccessToken = publishAccessToken(username);
        redisToken.updateAccessToken(newAccessToken);
        redisToken.updateRefreshToken(refreshToken);
        redisTokenRepository.save(redisToken);

        log.info("[JwtUtils] : Republish Token {}, {}", refreshToken, accessToken);

        response.setHeader("Access-Token", newAccessToken);

        // 해당 엑세스토큰 반환
        return newAccessToken;
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
    public String verifyAccessToken(String accessToken, HttpServletResponse response) {
        try {
            Jws<Claims> jws = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(accessToken);

            return jws.getPayload().getSubject();
        } catch (ExpiredJwtException expireException) {
            log.info("[JWT TOKEN] Token is Expired");
            String username = expireException.getClaims().getSubject();

            // ✅ username으로 서버 저장소의 refreshToken을 찾아서 재발행
            Optional<RedisToken> saved = redisTokenRepository.findById(username);

            if (saved.isEmpty())
                return null;

            String newAccess = republishAccessToken(saved.get().getRefreshToken(), response); // ✅ 여기서는 refreshToken을 넣음
            return null; // 여기서는 단순 검증 메소드니까 subject를 돌려주지 않음(필요시 새 AT를 반환하는 별도 메소드 도입)
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 리프레시 토큰 검증
     */
    public boolean verifyRefreshToken(String refreshToken) {
        try {
            Jws<Claims> claimsJws = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(refreshToken);

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 토큰에 대한 사용자 정보 조회
     * @param token - 인증된 엑세스 토큰
     * @return - 토큰 소유자 정보
     */
    public String getUsernameByToken(String token) {
        if(token == null || token.isBlank()) {
            throw new RuntimeException();
        }

        try {
            return Jwts.
                    parser().
                    verifyWith(secretKey).
                    build().
                    parseSignedClaims(token).
                    getPayload().
                    getSubject();
        } catch (ExpiredJwtException e) {
            return e.getClaims().getSubject();
        } catch (Exception e) {
            log.info("[JwtUtils] Unknown Exception : {}", e.toString());
        }

        return null;
    }
}