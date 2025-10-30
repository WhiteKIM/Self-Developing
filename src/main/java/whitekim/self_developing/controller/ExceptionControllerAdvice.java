package whitekim.self_developing.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import whitekim.self_developing.exception.ExpiredTokenException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    /**
     * 리프레시 토큰 만료로 인한 예외 발생
     * @param e - 토큰만료예외
     * @return
     */
    @ExceptionHandler(ExpiredTokenException.class)
    public ResponseEntity<Map<String, Object>> handleExpiredTokenException(ExpiredTokenException e) {
        Map<String, Object> body = new HashMap<>();
        body.put("status", 401);
        body.put("error", "SESSION_EXPIRED");
        body.put("message", e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(body);
    }
}
