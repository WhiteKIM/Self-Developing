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

    @ManyToOne
    @JoinColumn(name = "paper_id")
    private Paper paper;

    private String comment; //해설

    public void update(Problem updateProblem) {
        this.title = updateProblem.getTitle();
        this.round = updateProblem.getRound();
        this.subject = updateProblem.getSubject();
        this.problem = updateProblem.getProblem();
        this.image = updateProblem.getImage();
        this.comment = updateProblem.getComment();
    }

    public void appendedPaper(Paper paper) {
        this.paper = paper;
    }
}
