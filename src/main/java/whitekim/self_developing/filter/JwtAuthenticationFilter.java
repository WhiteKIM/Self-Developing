package whitekim.self_developing.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

/**
 * 인증 필터
 * 로그인 요청 시 해당 정보를 검증하고, jwt토큰을 발행한다.
 */

public class JwtAuthenticationFilter {

}
