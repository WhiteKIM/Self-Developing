package whitekim.self_developing.event.login;

import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

/**
 * 로그인 실패 이벤트 리스너
 * 로그인 실패 시 해당 실패 횟수를 기록해서 브루트포스 공격에 대비
 */
@Component
public class LoginFailEventListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {
    @Override
    public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
        
    }
}
