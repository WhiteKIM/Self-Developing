package whitekim.self_developing.model;

import jakarta.persistence.Entity;
import lombok.Getter;

import java.math.BigDecimal;

/**
 * @author whitekim
 * 사용자 포인트 및 포인트 이력 등을 관리하는 항목
 */
@Entity
@Getter
public class Point extends BaseEntity {
    private BigDecimal pointQuantity = BigDecimal.ZERO;

    public void addPoint(BigDecimal rewardPoint) {
        this.pointQuantity = pointQuantity.add(rewardPoint);
    }
}
