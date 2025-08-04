package whitekim.self_developing.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 * 문제집, 문제에 대한 태그 정보 제공
 */
@Entity
public class Tag extends BaseEntity {
    private String tagName;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}
