package whitekim.self_developing.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import whitekim.self_developing.exception.PermissionDeniedException;
import whitekim.self_developing.jwt.JwtUtils;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class TokenController {
    private final JwtUtils jwtUtils;

    /**
     * 리프레스 토큰으로 엑세스토큰 재발행
     * @param refreshToken
     * @return
     */
    @PostMapping("/publish/access")
    public ResponseEntity<String> republishAccessToken(@RequestHeader(name = "Request-Token") String refreshToken) {
        String accessToken = "";

        try {
            accessToken = jwtUtils.republishToken(refreshToken);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.toString());
        }

        return ResponseEntity.ok(accessToken);
    }

    /**
     * 사용자 토큰 정보 제거
     */
    @DeleteMapping("/remove")
    public ResponseEntity<String> removeToken(
            @RequestHeader(name = "Request-Token") String refreshToken,
            @RequestHeader(name = "Access-Token") String accessToken,
            @RequestParam String username) {
        try {
            jwtUtils.removeMemberToken(username, refreshToken, accessToken);
        } catch (PermissionDeniedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.toString());
        }

        return ResponseEntity.ok("token Remove Success");
    }
}
