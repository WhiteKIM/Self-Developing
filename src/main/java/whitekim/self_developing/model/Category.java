package whitekim.self_developing.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 카테고리 정보
 */
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Category extends BaseEntity {
    @Column(unique = true)
    private String categoryName;
}
