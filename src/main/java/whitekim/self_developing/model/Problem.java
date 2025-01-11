package whitekim.self_developing.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@DiscriminatorColumn(name = "DTYPE")
@Inheritance(strategy = InheritanceType.JOINED)
@SuperBuilder
public abstract class Problem extends BaseEntity {
    private String title;
    private String round;
    private String subject;//과목정보
    private String problem;// 문제내용 - 텍스트

    @OneToOne
    @JoinColumn(name = "certification_id")
    private Certification certification;    // 자격증정보

    @OneToOne
    @JoinColumn(name = "image_id")
    private Image image; // 문제내용 - 이미지

    private String comment; //해설
}
