package whitekim.self_developing.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import whitekim.self_developing.auth.PrincipalMember;
import whitekim.self_developing.model.Member;
import whitekim.self_developing.model.ProblemHistory;
import whitekim.self_developing.repository.MemberRepository;
import whitekim.self_developing.repository.ProblemHistoryRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class ProblemHistoryService {
    private final ProblemHistoryRepository historyRepository;
    private final MemberRepository memberRepository;

    public void createProblemHistory(ProblemHistory history) {
        // 인증정보를 가져와서 사용자 정보를 불러와야해
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        PrincipalMember detailMember = (PrincipalMember) auth.getPrincipal();
        Member authMember = detailMember.getMember();

        ProblemHistory problemHistory = historyRepository.save(history);
        Member member = memberRepository.findById(authMember.getId()).orElseThrow();

        if(member.getProblemHistoryList().size() + 1 > 30) {
            member.getProblemHistoryList().removeFirst();
        }

        member.addProblemHistory(problemHistory);
    }
}
