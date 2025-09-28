package whitekim.self_developing.event.Login;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import whitekim.self_developing.auth.PrincipalMember;
import whitekim.self_developing.service.MemberService;

@Component
@RequiredArgsConstructor
@Slf4j
public class LoginSuccessEventListener implements ApplicationListener<AuthenticationSuccessEvent> {

    private final MemberService memberService;

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        log.info("[LoginSuccessEvent] Success Login");

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
