package whitekim.self_developing.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author whitekim
 * 사용자 포인트 및 포인트 이력 등을 관리하는 항목
 */
@Entity
@Getter
public class Point extends BaseEntity {
    private BigDecimal pointQuantity = BigDecimal.ZERO;

    @OneToMany
    @JoinColumn(name = "log_id")
    private List<Log> logList = new ArrayList<>();

    public void addPoint(BigDecimal rewardPoint) {
        this.pointQuantity = pointQuantity.add(rewardPoint);
    }
}
