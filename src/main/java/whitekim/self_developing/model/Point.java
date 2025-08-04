package whitekim.self_developing.model;

import jakarta.persistence.Entity;

import java.math.BigDecimal;

@Entity
public class Point extends BaseEntity {
    private BigDecimal point;
    private String acquireReason;
}
