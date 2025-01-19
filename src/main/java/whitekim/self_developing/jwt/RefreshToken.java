package whitekim.self_developing.jwt;

import java.time.LocalDateTime;

/**
 * 리프레시 토큰
 * 사용자가 최초로 인증하는 경우나 기존 리프레시 토큰이 만료된 경우에 발행
 * 해당 토큰을 이용하여 엑세스 토큰을 발행함
 */
public class RefreshToken {
    private String username;            // 사용자명
    private LocalDateTime issueTime;    // 발행시간

    public boolean compareUsername(String username) {
        return this.username.equals(username);
    }
}
