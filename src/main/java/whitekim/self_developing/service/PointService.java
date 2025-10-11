package whitekim.self_developing.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import whitekim.self_developing.model.Member;
import whitekim.self_developing.model.Point;
import whitekim.self_developing.model.Problem;
import whitekim.self_developing.properties.PointProperties;
import whitekim.self_developing.repository.PointRepository;

import java.math.BigDecimal;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PointService {
    private final PointRepository pointRepository;

    private final PointProperties pointProperties;

    /**
     * 포인트 생성 후 저장
     * @return
     */
    public Point createEmptyPoint() {
        Point point = new Point();

        return pointRepository.save(point);
    }

    /**
     * 문제를 해결한 유저에 대해서 포인트를 지급한다.
     * 지급되는 포인트는 문제 난이도에 비례하여 지급한다.
     * @param member - 답안작성자
     * @param problem - 해결한 문제
     */
    public void earnPoint(Member member, Problem problem) {
        Point memberPoint = member.getPoint();

        // 다른 엔티티에 트랜잭션이 아닌곳에 접근하면 안돼요
        //this.logList.add(new Log("Point", "create", "pay a Point"));

        // 일단 문제 난이도에 비례하여 포인트를 지급한다.
        long pointRate = pointProperties.getPayRate();

        // 포인트 지급방식 = 문제 난이도 * 포인트 지급비율

        BigDecimal rewardPoint = BigDecimal.valueOf(pointRate).multiply(
                BigDecimal.valueOf(problem.getDifficulty())
        );

        log.info("[PointService] Rate : {}, Difficult : {} Reward Point : {}", pointRate, problem.getDifficulty(), rewardPoint);

        memberPoint.addPoint(rewardPoint);

        pointRepository.save(memberPoint);
    }

    /**
     * 문제 해결에 대한 포인트 지급 기능
     * 포인트는 문제 난이도와 지급율에 비례하여 지급됨
     * @param problem - 포인트 지급대상 문제 정보
     */
    private void payPoint(Problem problem) {

    }
}
