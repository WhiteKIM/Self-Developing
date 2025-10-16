package whitekim.self_developing.aop;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import whitekim.self_developing.service.MemberService;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class AccessPaperAspect {
    private final MemberService memberService;

    @AfterReturning(
            pointcut = "execution (* whitekim.self_developing.controller.PaperController.getDetailPaper(..))"
    )
    public void addPaperHistory(JoinPoint joinPoint) {
        log.info("[PaperAccessAspect] Add Recent Paper Info");

        Object[] args = joinPoint.getArgs();
        
        Long paperId = (Long) args[0];   // 문제집 정보 조회

        // 접근중인 정보가 존재하지 않는다.
        if(paperId == null)
            return;

        memberService.addPaperIntoRecentList(paperId);
    }
}
