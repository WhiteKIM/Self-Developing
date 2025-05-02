package whitekim.self_developing.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.PermissionDeniedDataAccessException;
import org.springframework.stereotype.Component;
import whitekim.self_developing.exception.PermissionDeniedException;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@Slf4j
public class JwtUtils {
    public static JwtUtils jwtUtils;

    private SecretKey secretKey = null; // 시크릿 키
    private final String key;
    private final Long refreshExpirationTime; // 리프레시토큰 만료시간
    private final Long accessExpirationTime; // 엑세스토큰 만료시간
    private final Map<String, RefreshToken> tokenStore = new ConcurrentHashMap<>();   // 토근 저장소
    
    public JwtUtils(@Value("${secret.key}") String key, @Value("${token.refresh-token.expiration}") Long refreshExpirationTime, @Value("${token.access-token.expiration}") Long accessExpirationTime) {
        this.refreshExpirationTime = refreshExpirationTime;
        this.accessExpirationTime = accessExpirationTime;
        this.key = key;
        this.secretKey = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 실질적으로 토큰 발행을 수행
     * 해당 사용자가 이전에 로그인하여 유효한 리프레시 토큰이 존재한다면 해당정보로 엑세스 토큰 발행
     * 없다면 리프레시 토큰과 엑세스 토큰을 발행
     * @param username - 로그인한 사용자
     */
    /* @BUG : 해당 코드에서 username으로 Map이 세팅되어 중복로그인이 차단되는 문제가 발생 */
    public void publishToken(String username, HttpServletResponse response) {
        // 이전에 발행된 리프레시 토큰이 존재하고, 해당 토큰이 유효한 경우
        if(tokenStore.containsKey(username) && !verifyRefreshToken(tokenStore.get(username).getToken()).isBlank()) {
            AccessToken accessToken = publishAccessToken(username);
            response.setHeader("Access-Token", accessToken.getToken());
            return;
        }

        // 리프레시 토큰도 유효하지 않은 경우
        RefreshToken refreshToken = publishRefreshToken(username);
        AccessToken accessToken = publishAccessToken(username);
        response.setHeader("Refresh-Token", refreshToken.getToken());
        response.setHeader("Access-Token", accessToken.getToken());
    }

    /**
     * 리프레시 토큰을 이용해서 엑세스 토큰 재발행 수행
     * 리프레시 토큰이 만료되었다면 오류 전달
     * @param refreshToken - 리프레시토큰
     */
    public String republishToken(String refreshToken) {
        // 이전에 발행된 리프레시 토큰이 존재하고, 해당 토큰이 유효한 경우
        String username = verifyRefreshToken(refreshToken);
        if(username == null) {
            throw new RuntimeException("만료된 인증정보입니다.");
        }

        // 리프레시토큰으로 엑세스 토큰 재발행 수행
        AccessToken accessToken = publishAccessToken(username);
        return accessToken.getToken();
    }


    /**
     * 리프레시 토근 발행
     */
    private RefreshToken publishRefreshToken(String username) {
        String token = Jwts.builder()
                .subject(username)
                .signWith(secretKey)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + refreshExpirationTime))
                .compact();

        RefreshToken refreshToken = new RefreshToken(username, token);
        tokenStore.put(username, refreshToken);

        return refreshToken;
    }

    /**
     * 엑세스 토근 발행
     */
    private AccessToken publishAccessToken(String username) {
        String token = Jwts.builder()
                .subject(username)
                .signWith(secretKey)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + accessExpirationTime))
                .compact();

        return new AccessToken(username, token);
    }


    /**
     * 엑세스 토큰 검증
     */
    public String verifyAccessToken(String accessToken) {
        Jws<Claims> claimsJws = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(accessToken);

        if(!claimsJws.getPayload().getExpiration().before(new Date())) {
            // 만료된 토큰
            return null;
        }

        return claimsJws.getPayload().getSubject();
    }

    /**
     * 리프레시 토큰 검증
     */
    public String verifyRefreshToken(String refreshToken) {
        Jws<Claims> claimsJws = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(refreshToken);

        if(!claimsJws.getPayload().getExpiration().before(new Date())) {
            // 만료된 토큰
            return null;
        }

        return claimsJws.getPayload().getSubject();
    }

    /**
     * 특정 사용자 토큰 제거
     * 본인이 아닌 제3자의 요청만으로는 제거하지 않는다.
     * @param username     : 토큰을 제거할 사용자명
     * @param refreshToken accessToken : 검증대상 토큰
     */
    public void removeMemberToken(String username, String refreshToken, String accessToken) {
        if(verifyRefreshToken(refreshToken).equals(username) || verifyAccessToken(accessToken).equals(username)) {
            tokenStore.remove(username);
            return;
        }

        throw new PermissionDeniedException("권한이 없는 접근입니다.");
    }
}
