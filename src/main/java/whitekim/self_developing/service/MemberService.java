package whitekim.self_developing.service;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import whitekim.self_developing.auth.PrincipalMember;
import whitekim.self_developing.dto.request.LoginMember;
import whitekim.self_developing.dto.request.SubmitProblem;
import whitekim.self_developing.jwt.JwtUtils;
import whitekim.self_developing.model.Member;
import whitekim.self_developing.model.Paper;
import whitekim.self_developing.model.Problem;
import whitekim.self_developing.repository.MemberRepository;
import whitekim.self_developing.repository.PaperRepository;
import whitekim.self_developing.repository.ProblemRepository;
import whitekim.self_developing.service.factory.problem.ProblemRepoFactory;
import whitekim.self_developing.utils.AuthUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PaperRepository paperRepository;
    private final ProblemRepoFactory repoFactory;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public Optional<Member> findById(Long id) {
        return memberRepository.findById(id);
    }

    public void registerMember(Member member) throws ConstraintViolationException {
        // 등록가능한 사용자인지 판단
        memberRepository.save(member);

        // 비밀번호 암호화
        String rawPassword = member.getPassword();
        String encPassword = passwordEncoder.encode(rawPassword);
        member.encryptPassword(encPassword);

        memberRepository.save(member);
    }

    /**
     * 로그인 기능 수행
     * @param member - 로그인 정보
     */
    public void loginMember(LoginMember member, HttpServletResponse response) {
        Optional<Member> optionalMember = memberRepository.findByUsername(member.username());

        if(optionalMember.isEmpty())
            throw new UsernameNotFoundException("Not Correct UserName");

        Member loginMember = optionalMember.get();

        if(!passwordEncoder.matches(member.password(), loginMember.getPassword())) {
            throw new RuntimeException("Password is Not Matched");
        }

        jwtUtils.publishToken(loginMember.getUsername(), response);
    }

    /**
     * 사용자 정보 업데이트
     * @param updateInfo - 업데이트할 사용자 정보
     */
    public void modifyMemberInfo(Member updateInfo) {
        
    }

    /**
     * 사용자 비밀번호 초기화
     * @param id - 사용자 PK
     * @return - 변경된 비밀번호
     */
    public String resetMemberPassword(Long id) {
        Member targetMember = memberRepository.findById(id).orElseThrow(RuntimeException::new);
        String uuid = UUID.randomUUID().toString().replace("-", "").substring(0, 16);
        String password = passwordEncoder.encode(uuid);
        targetMember.resetPassword(password);

        return uuid;
    }

    /**
     * 즐겨찾기 추가
     */
    public void addPaperIntoFavoriteList(Long paperId) {
        PrincipalMember userDetails = (PrincipalMember) AuthUtils.getAuthentication();
        Long loginMemberId = userDetails.getId();

        Member loginMember = memberRepository.findById(loginMemberId).orElseThrow(RuntimeException::new);
        Paper paper = paperRepository.findById(paperId).orElseThrow(RuntimeException::new);

        loginMember.addFavorite(paper);
    }

    /**
     * 최근 조회 항목 추가
     * @param paperId
     */
    public void addPaperIntoRecentList(Long paperId) {
        PrincipalMember userDetails = (PrincipalMember) AuthUtils.getAuthentication();
        Long loginMemberId = userDetails.getId();

        Member loginMember = memberRepository.findById(loginMemberId).orElseThrow(RuntimeException::new);
        Paper paper = paperRepository.findById(paperId).orElseThrow(RuntimeException::new);

        loginMember.addRecentPaper(paper);
    }

    /**
     * 틀린 오답문제 내역을 추가
     * @param wrongProblemList
     */
    public void addProblemIntoWrongList(List<SubmitProblem> wrongProblemList) {
        PrincipalMember userDetails = (PrincipalMember) AuthUtils.getAuthentication();
        Long loginMemberId = userDetails.getId();

        Member loginMember = memberRepository.findById(loginMemberId).orElseThrow(RuntimeException::new);

        for(SubmitProblem submitProblem : wrongProblemList) {
            ProblemRepository<? extends Problem> repository = repoFactory.createRepository(submitProblem.getType());
            Problem problem = repository.findById(submitProblem.getId()).orElseThrow();
            loginMember.addWrongProblem(problem);
        }
    }
}
