package whitekim.self_developing.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import whitekim.self_developing.auth.PrincipalMember;
import whitekim.self_developing.dto.request.SubmitProblem;
import whitekim.self_developing.dto.request.UpdateMember;
import whitekim.self_developing.dto.response.MemberInfo;
import whitekim.self_developing.jwt.JwtUtils;
import whitekim.self_developing.model.Member;
import whitekim.self_developing.model.Paper;
import whitekim.self_developing.model.Point;
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
@Slf4j
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PaperRepository paperRepository;
    private final ProblemRepoFactory repoFactory;
    private final PasswordEncoder passwordEncoder;
    private final PointService pointService;
    private final JwtUtils jwtUtils;

    public Optional<Member> findById(Long id) {
        return memberRepository.findById(id);
    }

    /**
     * 사용자정보 최초 생성
     * @param member
     * @throws ConstraintViolationException
     */
    public void registerMember(Member member) throws ConstraintViolationException {
        Point emptyPoint = pointService.createEmptyPoint();
        member.setPoint(emptyPoint);        // 빈 포인트와 관계 설정
        
        // 등록가능한 사용자인지 판단
        memberRepository.save(member);

        // 비밀번호 암호화
        String rawPassword = member.getPassword();
        String encPassword = passwordEncoder.encode(rawPassword);
        member.encryptPassword(encPassword);

        memberRepository.save(member);
    }

    /**
     * 사용자정보 상세조회
     * @param member - 로그인 정보
     */
    public MemberInfo getMemberDetail(Member member) {
        Member targetMember = memberRepository.findById(member.getId()).orElseThrow();

        return MemberInfo.from(targetMember);
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

        log.info("[PaperService] Login Member Info : {}", userDetails);
        Member loginMember = memberRepository.findById(loginMemberId).orElseThrow(RuntimeException::new);
        Paper paper = paperRepository.findById(paperId).orElseThrow(RuntimeException::new);

        loginMember.addFavorite(paper);
    }

    /**
     * 즐겨찾기 제거
     * @param paperId
     */
    public void removePaperFromFavoriteList(Long paperId) {
        PrincipalMember userDetails = (PrincipalMember) AuthUtils.getAuthentication();
        Long loginMemberId = userDetails.getId();

        log.info("[PaperService] Login Member Info : {}", userDetails);

        Member loginMember = memberRepository.findById(loginMemberId).orElseThrow(RuntimeException::new);
        Paper paper = paperRepository.findById(paperId).orElseThrow(RuntimeException::new);

        loginMember.removeFavorite(paper);
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
     * @param wrongProblemList - 틀린 문제 히스토리 리스트
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

    /**
     * 사용자 로그인 성공 시 로그 기록
     * @param member - 로그인 사용자
     * @param remoteIpAddress - 로그인 IP
     */
    public void updateLoginSuccessInfo(Member member, String remoteIpAddress) {
        member.updateLoginIPAddress(remoteIpAddress);
    }


    /**
     * 사용자 로그인 실패 시 실패카운트 기록
     * @param username - 로그인에 실패한 사용자 정보
     */
    public void updateLoginFailInfo(String username) {
        Member member = memberRepository.findByUsername(username).orElseThrow();

        member.updateLoginFail();
    }

    /**
     * 사용자 IP 주소 검출 기능
     * @return - 검출된 IP 주소
     */
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

    /**
     * 사용자 정보 수정
     * @param updateMember - 업데이트 대상
     * @param updateMemberInfo - 업데이트정보
     */
    public void updateMember(Member updateMember, UpdateMember updateMemberInfo) {

    }

    /**
     * 사용자 즐겨찾기 리스트 조회
     * @param id
     * @return
     */
    public MemberInfo getMemberFavoriteList(Long id) {
        Member member = memberRepository.findMemberWithFavoriteList(id).orElseThrow();

        return MemberInfo.from(member);
    }

    /**
     * 사용자 최근내역 리스트 조회
     * @param id
     * @return
     */
    public MemberInfo getMemberRecentList(Long id) {
        Member member = memberRepository.findMemberWithRecentList(id).orElseThrow();

        return MemberInfo.from(member);
    }
}
