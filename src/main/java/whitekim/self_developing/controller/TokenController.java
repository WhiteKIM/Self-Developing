package whitekim.self_developing.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import whitekim.self_developing.jwt.JwtUtils;

@RestController
@RequiredArgsConstructor
public class TokenController {
    private final JwtUtils jwtUtils;

    /**
     * 리프레스 토큰으로 엑세스토큰 재발행
     * @param refreshToken
     * @return
     */
    @PostMapping("/auth/refresh")
    public ResponseEntity<String> republishAccessToken(@RequestBody String refreshToken) {
        String accessToken = "";

        try {
            accessToken = jwtUtils.republishToken(refreshToken);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        return ResponseEntity.ok(accessToken);
    }
}
