package whitekim.self_developing.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 문제집들에 대한 페이지
 * 카테고리도 문제집이 아니라 여기에 포함되어야 할듯
 */

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Page extends BaseEntity {
    private PageType pageType;

    @OneToOne
    @JoinColumn(name = "certification_id")
    private Certification certification;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "page")
    @Builder.Default
    private List<Paper> paperList = new ArrayList<>();
}
