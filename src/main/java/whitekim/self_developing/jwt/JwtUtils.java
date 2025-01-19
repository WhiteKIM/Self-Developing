package whitekim.self_developing.jwt;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class JwtUtils {
    public static JwtUtils jwtUtils;

    private final String secretKey; // 시크릿 키
    private final LocalDateTime refreshExpirationTime; // 리프레시토큰 만료시간
    private final LocalDateTime accessExpirationTime; // 엑세스토큰 만료시간
    private final Map<RefreshToken, AccessToken> tokenStore = new ConcurrentHashMap<>();   // 토근 저장소
    
    public JwtUtils(String secretKey, LocalDateTime refreshExpirationTime, LocalDateTime accessExpirationTime) {
        this.secretKey = secretKey;
        this.refreshExpirationTime = refreshExpirationTime;
        this.accessExpirationTime = accessExpirationTime;
    }

    /**
     * 토근 발행
     */
    public RefreshToken publishToken() {
        return null;
    }

    /**
     * 토큰 검증
     */
    public boolean isVaildation(AccessToken accessToken) {
        return true;
    }

    /**
     * 특정 사용자 토큰 제거
     * @param username : 토큰을 제거할 사용자명
     */
    public void removeMemberToken(String username) {
        for(RefreshToken refreshToken : tokenStore.keySet()) {
            if(refreshToken.compareUsername(username)) {
                tokenStore.remove(refreshToken);
                return;
            }
        }
    }
}
