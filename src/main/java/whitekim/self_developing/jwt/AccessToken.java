package whitekim.self_developing.jwt;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 엑세스토큰
 * 리프레시 토큰을 이용해서 발행되고, 실질적으로 인가를 위해서 사용하는 토큰
 */
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class AccessToken {
    private String username;   // 사용자명
    private String token;      // 토큰
}
