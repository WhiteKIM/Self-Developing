package whitekim.self_developing.event.login;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;
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

    }

    @Override
    public boolean supportsAsyncExecution() {
        return ApplicationListener.super.supportsAsyncExecution();
    }
}
