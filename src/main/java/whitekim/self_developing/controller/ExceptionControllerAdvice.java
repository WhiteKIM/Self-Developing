package whitekim.self_developing.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import whitekim.self_developing.exception.ExpiredTokenException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class ExceptionControllerAdvice {

    /**
     * 리프레시 토큰 만료로 인한 예외 발생
     * @param e - 토큰만료예외
     * @return
     */
    @ExceptionHandler(ExpiredTokenException.class)
    public ResponseEntity<Map<String, Object>> handleExpiredTokenException(ExpiredTokenException e) {
        log.error("[ExceptionController] Token Expired");

        Map<String, Object> body = new HashMap<>();
        body.put("status", 401);
        body.put("error", "SESSION_EXPIRED");
        body.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
    }

    /**
     * 사용자정보가 존재하지 않는 경우
     * @param e - 존재하지않는사용자정보 예외
     * @return
     */
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleUsernameNotFound(UsernameNotFoundException e) {
        log.error("[ExceptionController] Not Exist User Info");

        Map<String, Object> body = new HashMap<>();
        body.put("status", 401);
        body.put("error", "SESSION_EXPIRED");
        body.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
    }
}
