package whitekim.self_developing.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import whitekim.self_developing.model.Vote;
import whitekim.self_developing.model.enumerate.Reaction;
import whitekim.self_developing.repository.VoteRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class VoteService {
    private final VoteRepository voteRepository;

    public Vote addVote(String type) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String ipAddr = getIpAddr();
        
        String username = auth.getName();
        Vote vote = new Vote(Reaction.fromValue(type), username, ipAddr);
        return voteRepository.save(vote);
    }
    
    private String getIpAddr() {
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
}
