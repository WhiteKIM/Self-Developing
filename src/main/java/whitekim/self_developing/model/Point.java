package whitekim.self_developing.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author whitekim
 * 사용자 포인트 및 포인트 이력 등을 관리하는 항목
 */
@Entity
public class Point extends BaseEntity {
    private BigDecimal pointQuantity = BigDecimal.ZERO;

    @OneToMany
    @JoinColumn(name = "log_id")
    private List<Log> logList = new ArrayList<>();

    /**
     * 문제 해결에 대한 포인트 지급 기능
     * 포인트는 문제 난이도와 지급율에 비례하여 지급됨
     * @param problem - 포인트 지급대상 문제 정보
     */
    public void payPoint(Problem problem) {
        this.logList.add(new Log("Point", "create", "pay a Point"));

        // 일단 문제 난이도에 비례하여 포인트를 지급한다.
        long pointRate = Long.parseLong(System.getProperty("point.pay_rate"));

        // 포인트 지급방식 = 문제 난이도 * 포인트 지급비율
        pointQuantity = pointQuantity.add(
                BigDecimal.valueOf(pointRate).multiply(
                        BigDecimal.valueOf(problem.getDifficulty()
                        )
                )
        );
    }
}
