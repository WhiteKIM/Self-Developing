package whitekim.self_developing.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import whitekim.self_developing.auth.PrincipalMember;
import whitekim.self_developing.dto.response.MarkingProblem;
import whitekim.self_developing.model.Member;
import whitekim.self_developing.model.Problem;
import whitekim.self_developing.service.PointService;
import whitekim.self_developing.service.ProblemService;

/**
 * @author WhiteKIM
 * 문제 해결 시 포인트 제공을 위한 포인트컷 기능
 */
@Component
@RequiredArgsConstructor
@Slf4j
@Aspect
public class PointRewardAspect {
    private final PointService pointService;

    @AfterReturning(
            pointcut = "execution(* whitekim.self_developing.service..ProblemService+.markingProblem(..)) && target(problemService)",
            returning = "result"
    )
    public void payPoint(JoinPoint joinPoint, ProblemService problemService, MarkingProblem result) {
        log.info("[PointReward] Obtain Point Aspect");

        Object[] args = joinPoint.getArgs();

        Long problemId = (Long) args[0];

        // 인증정보를 가져와서 사용자 정보를 불러와야해
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        PrincipalMember detailMember = (PrincipalMember) auth.getPrincipal();
        Member authMember = detailMember.getMember();

        // 문제 정보 조회
        Problem problem = (Problem) problemService.getProblem(problemId).orElseThrow();

        // 결과 불러오기
        if(result.isCorrect()) {
            pointService.earnPoint(authMember, problem);
        }
    }
}
