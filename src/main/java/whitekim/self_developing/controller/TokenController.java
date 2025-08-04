package whitekim.self_developing.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import whitekim.self_developing.exception.PermissionDeniedException;
import whitekim.self_developing.jwt.JwtUtils;

@RestController
@RequiredArgsConstructor
@RequestMapping("/token")
public class TokenController {
    private final JwtUtils jwtUtils;

    /**
     * 사용자 토큰 정보 제거
     */
    @DeleteMapping("/v1/remove")
    public ResponseEntity<String> removeToken(
            @RequestHeader(name = "Request-Token") String refreshToken,
            @RequestHeader(name = "Access-Token") String accessToken,
            @RequestParam String username) {
        try {
            jwtUtils.removeMemberToken(username);
        } catch (PermissionDeniedException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.toString());
        }

        return ResponseEntity.ok("token Remove Success");
    }
}
