package whitekim.self_developing.event.login;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import whitekim.self_developing.auth.PrincipalMember;
import whitekim.self_developing.service.MemberService;

/**
 * 로그인 성공 이벤트 리스너
 * 성공 시 접속한 IP와 시간을 기록한다.
 */
@Component
@RequiredArgsConstructor
public class LoginSuccessEventListener implements ApplicationListener<AuthenticationSuccessEvent> {
    private final MemberService memberService;

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        Authentication auth = event.getAuthentication();
        PrincipalMember principalMember = (PrincipalMember) auth.getPrincipal();

        memberService.updateLoginSuccessInfo(principalMember.getMember(), getRemoteIpAddress());
    }

    @Override
    public boolean supportsAsyncExecution() {
        return ApplicationListener.super.supportsAsyncExecution();
    }

    private String getRemoteIpAddress() {
        ServletRequestAttributes requestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();

        if(requestAttributes == null) {
            return null;
        }

        HttpServletRequest request = requestAttributes.getRequest();

        // 프록시/로드밸런서 환경 고려
        String xff = request.getHeader("X-Forwarded-For");
        if (xff != null && !xff.isBlank()) {
            // 첫 번째가 원 클라이언트 IP
            return xff.split(",")[0].trim();
        }

        String xri = request.getHeader("X-Real-IP");
        if (xri != null && !xri.isBlank())
            return xri.trim();

        return request.getRemoteAddr();
    }
}
