package whitekim.self_developing.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import whitekim.self_developing.model.Log;
import whitekim.self_developing.model.Member;
import whitekim.self_developing.model.Point;
import whitekim.self_developing.model.Problem;
import whitekim.self_developing.properties.PointProperties;
import whitekim.self_developing.repository.LogRepository;
import whitekim.self_developing.repository.PointRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PointService {
    private final PointRepository pointRepository;
    private final PointProperties pointProperties;
    private final LogRepository logRepository;

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

        // 포인트 로그 생성
        Log log = Log.builder()
                .job("[PointLog]")
                .method("earnPoint")
                .message(
                        String.format("%s : %s 사용자가 %d 문제를 해결하여 %s 포인트를 획득하였습니다.",
                                LocalDateTime.now().toString(),
                                member.getUsername(),
                                problem.getId(),
                                rewardPoint.toString()
                        )
                )
                .build();

        log = logRepository.save(log);

        memberPoint.addPoint(rewardPoint, log);

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
