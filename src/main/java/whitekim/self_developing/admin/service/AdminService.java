package whitekim.self_developing.admin.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;
import whitekim.self_developing.dto.request.LoginMember;
import whitekim.self_developing.exception.PermissionDeniedException;
import whitekim.self_developing.model.Member;
import whitekim.self_developing.model.enumerate.Permission;
import whitekim.self_developing.repository.MemberRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class AdminService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 관리자 로그인 
     * @param loginMember - 로그인정보
     */
    public void loginAdmin(LoginMember loginMember, HttpServletRequest request) {
        Optional<Member> optLoginUser =
                memberRepository.findByUsername(loginMember.username());

        if(optLoginUser.isEmpty()) {
            throw new UsernameNotFoundException("존재하지 않는 사용자입니다.");
        }

        Member loginUser = optLoginUser.get();

        if(loginUser.getPermission() != Permission.ADMIN) {
            throw new PermissionDeniedException("접근권한이 없습니다.");
        }

        if(!passwordEncoder.matches(loginMember.password(), loginUser.getPassword())) {
            throw new RuntimeException("잘못된 비밀번호입니다.");
        }

        log.info("[AdminService] User Permission : " + loginUser.getPermission().getRole());

        Authentication auth = new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword(), List.of(new SimpleGrantedAuthority(loginUser.getPermission().getRole())));

        SecurityContextHolder.getContext().setAuthentication(auth);

        HttpSession session = request.getSession(true);
        session.setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                SecurityContextHolder.getContext()
        );
    }
}
