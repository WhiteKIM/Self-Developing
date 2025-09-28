package whitekim.self_developing.jwt.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import whitekim.self_developing.auth.PrincipalMember;
import whitekim.self_developing.dto.request.LoginMember;
import whitekim.self_developing.jwt.JwtUtils;

import javax.security.auth.login.LoginException;
import java.io.IOException;

/**
 * 인증 필터
 * 로그인 요청 시 해당 정보를 검증하고, jwt토큰을 발행한다.
 */
@Component
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final JwtUtils jwtUtils;
    private final ObjectMapper objectMapper;

    /**
     * 최초 생성자
     * 로그인주소 정보 및 사용할 인스턴스 주입 처리
     * @param authenticationManager
     */
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtUtils jwtUtils, ObjectMapper objectMapper) {
        super(authenticationManager);
        this.jwtUtils = jwtUtils;
        this.objectMapper = objectMapper;
        setFilterProcessesUrl("/api/member/login");
    }

    /**
     * 사용자 인증 처리
     * @param request
     * @param response
     * @return
     * @throws AuthenticationException
     */
    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        LoginMember loginMember = objectMapper.readValue(request.getInputStream(), LoginMember.class);

        if(loginMember == null)
            throw new LoginException("로그인 정보가 존재하지 않습니다.");

        // 해당 정보로 로그인 토큰 생성
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(loginMember.username(), loginMember.password());


        // 로그인 인증 수행
        return getAuthenticationManager().authenticate(token);
    }

    /**
     * 로그인 성공 시 JWT토큰 발급 수행
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        PrincipalMember principalMember = (PrincipalMember) authResult.getPrincipal();

        jwtUtils.publishToken(principalMember.getUsername(), response);
    }

}
