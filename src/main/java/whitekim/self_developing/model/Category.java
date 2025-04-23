package whitekim.self_developing.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 카테고리 정보
 */
@Entity
@Getter
@AllArgsConstructor
public class Category extends BaseEntity {
    @Column(unique = true)
    private String categoryName;
}
