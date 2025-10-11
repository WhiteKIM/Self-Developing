package whitekim.self_developing.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import whitekim.self_developing.auth.PrincipalMember;
import whitekim.self_developing.dto.response.MarkingProblem;
import whitekim.self_developing.model.Member;
import whitekim.self_developing.model.Problem;
import whitekim.self_developing.model.ProblemHistory;
import whitekim.self_developing.service.ProblemHistoryService;
import whitekim.self_developing.service.ProblemService;

/**
 * @author whitekim
 * 문제 풀이 시 해당 액션에 대한 이력 생성
 */
@Aspect
@RequiredArgsConstructor
@Slf4j
public class ProblemSolvingAspect {
    private final ProblemHistoryService historyService;

    @AfterReturning(
            pointcut = "execution(* whitekim.self_developing.service..ProblemService+.markingProblem(..))",
            returning = "solvingProblem"
    )
    public void solvedProblem(JoinPoint joinPoint, ProblemService problemService, MarkingProblem solvingProblem) {
        log.info("[ProblemSolvingAspect] Logging Problem Solved History");

        // 인증정보를 가져와서 사용자 정보를 불러와야해
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        PrincipalMember detailMember = (PrincipalMember) auth.getPrincipal();
        Member authMember = detailMember.getMember();

        // 문제 정보 조회
        Problem solvedProblem = (Problem) problemService.getProblem(solvingProblem.problemId()).orElseThrow();
        
        // 문제 이력 생성
        historyService.createProblemHistory(new ProblemHistory(
                solvingProblem.isCorrect(),
                solvedProblem,
                authMember
        ));
    }
}
