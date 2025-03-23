package whitekim.self_developing.model;

import jakarta.persistence.*;
import lombok.Getter;

/**
 * 카테고리 정보
 */
@Entity
@Getter
public class Category extends BaseEntity {
    @Column(unique = true)
    private String categoryName;
}
