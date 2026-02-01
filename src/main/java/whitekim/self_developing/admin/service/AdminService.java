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
import whitekim.self_developing.admin.mapper.MemberManagementMapper;
import whitekim.self_developing.admin.mapper.StatMapperRepository;
import whitekim.self_developing.admin.model.MainStatInfo;
import whitekim.self_developing.admin.model.MemberInfo;
import whitekim.self_developing.admin.model.stat.CertPaperPrbStatInfo;
import whitekim.self_developing.admin.model.stat.DiffProblemStatInfo;
import whitekim.self_developing.admin.model.stat.MemberStatInfo;
import whitekim.self_developing.admin.model.stat.ProblemStatInfo;
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
    private final StatMapperRepository statMapperRepository;
    private final MemberManagementMapper memberManagementMapper;

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

    /**
     * 관리자 메인화면 차트 조회
     */
    public MainStatInfo selectStatInfo() {
        // 문제, 문제집 통계 조회
        CertPaperPrbStatInfo certPaperPrbStatInfo =
                statMapperRepository.selectCountPaperAndProblem() != null ?
                        statMapperRepository.selectCountPaperAndProblem() :
                        new CertPaperPrbStatInfo(0L,0L,0L,0L);

        // 신규 사용자 수 조회
        MemberStatInfo newMemberCount =
                statMapperRepository.selectNewMemberCount() != null ?
                        statMapperRepository.selectNewMemberCount() :
                        new MemberStatInfo(0L, 0L)
                ;

        // 일주일간 해결된 문제
        ProblemStatInfo problemStatInfo =
                statMapperRepository.selectProblemHistoryAgg() != null ?
                        statMapperRepository.selectProblemHistoryAgg() :
                        new ProblemStatInfo(0L, 0L, 0L);

        // 난이도별 문제 정오표 통계
        List<DiffProblemStatInfo> diffProblemStatInfo =
                statMapperRepository.selectDegreeLevelAgg() != null && !statMapperRepository.selectDegreeLevelAgg().isEmpty() ?
                        statMapperRepository.selectDegreeLevelAgg() :
                        List.of();

        MainStatInfo mainStatInfo = new MainStatInfo();
        mainStatInfo.setProblemStatInfo(problemStatInfo);
        mainStatInfo.setDiffProblemStatInfo(diffProblemStatInfo);
        mainStatInfo.setCertPaperPrbStatInfo(certPaperPrbStatInfo);
        mainStatInfo.setNewMemberCount(newMemberCount);

        return mainStatInfo;
    }

    /**
     * 사용자 관리화면 조회
     */
    public List<MemberInfo> selectMemberList() {
        return memberManagementMapper.selectMemberList();
    }

    /**
     * 사용자 계정 삭제처리
     */
    public void deleteMember(Long id) {
        memberManagementMapper.deleteMember(id);
    }
}
