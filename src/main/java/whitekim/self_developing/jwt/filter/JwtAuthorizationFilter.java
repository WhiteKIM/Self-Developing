package whitekim.self_developing.jwt.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import whitekim.self_developing.jwt.JwtUtils;

import java.io.IOException;

/**
 * 인가 필터
 * 해당 사용자가 인증완료된 사용자인지 아닌지를 판별한다.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthorizationFilter extends OncePerRequestFilter {
    private final JwtUtils jwtUtils;
    private final UserDetailsService userDetailsService;

    /**
     * 인가 필터링
     * 헤더에서 토큰 정보를 받아와 인가를 수행
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = request.getHeader("Access-Token");
        UserDetails userDetails;

        log.info("[AccessToken Info] : {}", accessToken);

        if(accessToken == null || accessToken.isBlank()) {
            filterChain.doFilter(request, response);
            return;
        }
        
        if(jwtUtils.verifyAccessToken(accessToken, response) == null || jwtUtils.verifyAccessToken(accessToken, response).isBlank()) {
            // 엑세스 토큰 만료
            // 리프레시 토큰이 유효하면 엑세스 토큰 재발행
            String accToken = jwtUtils.republishAccessToken(accessToken, response);
            String username = jwtUtils.getUsernameByAccessToken(accessToken);
            userDetails = userDetailsService.loadUserByUsername(username);
        } else {
            // 엑세스토큰 유효
            userDetails = userDetailsService.loadUserByUsername(jwtUtils.getUsernameByAccessToken(accessToken));
        }

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(token);
    }
}
