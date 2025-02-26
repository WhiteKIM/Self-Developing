package whitekim.self_developing.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import whitekim.self_developing.jwt.JwtUtils;

@RestController
@RequiredArgsConstructor
public class TokenController {
    private final JwtUtils jwtUtils;

    /**
     * 리프레시 토큰을 이용해서 엑세스 토큰을 재발행
     * @param response
     * @param request
     * @return
     */
    @GetMapping("/api/v1/reissue/accessToken")
    public ResponseEntity<?> reIssueAccessToken(HttpServletResponse response, HttpServletRequest request) {
        String token = response.getHeader("Refresh-Token");
        String username = jwtUtils.verifyRefreshToken(token);

        if(username.isBlank()) {
            return ResponseEntity.status(401).body("로그인 정보가 만료되었습니다.");
        }

        jwtUtils.publishToken(username, response);

        return ResponseEntity.ok("Success");
    }
}
