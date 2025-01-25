package whitekim.self_developing.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletResponse;
import whitekim.self_developing.exception.InValidationTokenException;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class JwtUtils {
    public static JwtUtils jwtUtils;

    private SecretKey secretKey = null; // 시크릿 키
    private final String key;
    private final Long refreshExpirationTime; // 리프레시토큰 만료시간
    private final Long accessExpirationTime; // 엑세스토큰 만료시간
    private final Map<String, RefreshToken> tokenStore = new ConcurrentHashMap<>();   // 토근 저장소
    
    public JwtUtils(String key, Long refreshExpirationTime, Long accessExpirationTime) {
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
    public void publishToken(String username, HttpServletResponse response) {
        // 이전에 발행된 리프레시 토큰이 존재하고, 해당 토큰이 유효한 경우
        if(tokenStore.containsKey(username) && verifyRefreshToken(tokenStore.get(username).getToken())) {
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
     * 토큰 검증 로직
     * 엑세스 토큰에 대해서 검증하고, 해당 토큰이 만료되거나 유효하지 않다면
     * 리프레쉬 토큰이 있다면 해당 토큰을 이용해서
     * @param response
     */
    public boolean verifyToken(HttpServletResponse response) {
        String accessToken = response.getHeader("Access-Token");
        String refreshToken = response.getHeader("Refresh-Token");

        // 엑세스 토큰으로만 요청한 경우
        if(refreshToken.isBlank()) {
            if(!verifyAccessToken(accessToken)) {
                throw new InValidationTokenException("Invalid Access Token");
            }

            return true;
        } else {
            if(!verifyRefreshToken(refreshToken)) {
                // 새롭게 토큰을 모두 발행해야 한다.
                throw new InValidationTokenException("Invalid Refresh Token");
            }

            Jws<Claims> claimsJws = Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(refreshToken);
            String username = claimsJws.getPayload().getSubject();
            AccessToken token = publishAccessToken(username);

            response.setHeader("Access-Token", token.getToken());

            return true;
        }
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
    public boolean verifyAccessToken(String accessToken) {
        return true;
    }

    /**
     * 리프레시 토큰 검증
     */
    public boolean verifyRefreshToken(String refreshToken) {
        return true;
    }

    /**
     * 특정 사용자 토큰 제거
     * @param username : 토큰을 제거할 사용자명
     */
    private void removeMemberToken(String username) {
        tokenStore.remove(username);
    }
}
