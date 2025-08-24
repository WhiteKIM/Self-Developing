package whitekim.self_developing.model;

import jakarta.persistence.Entity;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

/**
 * 문제, 문제집 등에 대해서 태그를 관리하기 위해서 사용
 */
@Entity
@Getter
public class Tag extends BaseEntity {
    @Length(min = 1, max = 20)
    private String tagName;

    public Tag(String tagName) {
        this.tagName = tagName;
    }
}
