package whitekim.self_developing.event.login;

import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;
import whitekim.self_developing.service.MemberService;

/**
 * 로그인 실패 이벤트 리스너
 * 로그인 실패 시 해당 실패 횟수를 기록해서 브루트포스 공격에 대비
 */
@Component
@RequiredArgsConstructor
public class LoginFailEventListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {
    private final MemberService memberService;

    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
        String username  = (String) event.getAuthentication().getPrincipal();

        memberService.updateLoginFailInfo(username);
    }
}
