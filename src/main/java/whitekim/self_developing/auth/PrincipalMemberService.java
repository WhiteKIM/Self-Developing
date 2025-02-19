package whitekim.self_developing.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import whitekim.self_developing.model.Member;
import whitekim.self_developing.repository.MemberRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PrincipalMemberService implements UserDetailsService {
    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Member> memberOptional = memberRepository.findByUsername(username);

        if(memberOptional.isEmpty())
            throw new UsernameNotFoundException("");

        // 이미 상위 메소드에서 검증이 끝났지만 한번더 수행
        return new PrincipalMember(memberOptional.get());
    }
}
