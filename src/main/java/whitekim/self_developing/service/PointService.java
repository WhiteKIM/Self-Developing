package whitekim.self_developing.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import whitekim.self_developing.model.Member;
import whitekim.self_developing.model.Point;
import whitekim.self_developing.model.Problem;
import whitekim.self_developing.repository.PointRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class PointService {
    private final PointRepository pointRepository;

    /**
     * 문제를 해결한 유저에 대해서 포인트를 지급한다.
     * 지급되는 포인트는 문제 난이도에 비례하여 지급한다.
     * @param member - 답안작성자
     * @param problem - 해결한 문제
     */
    public void earnPoint(Member member, Problem problem) {
        Point memberPoint = member.getPoint();
        memberPoint.payPoint(problem);
    }
}
