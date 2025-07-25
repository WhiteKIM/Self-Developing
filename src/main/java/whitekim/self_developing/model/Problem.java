package whitekim.self_developing.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import whitekim.self_developing.dto.request.ProblemForm;
import whitekim.self_developing.dto.response.MarkingProblem;

@Entity
@Getter
@DiscriminatorColumn(name = "DTYPE")
@Inheritance(strategy = InheritanceType.JOINED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public abstract class Problem extends BaseEntity {
    private String title;   // 제목
    private String round;   // 회차정보
    private String subject; //과목정보
    private String problem; // 문제내용 - 텍스트

    @Setter
    @OneToOne
    @JoinColumn(name = "certification_id")
    private Certification certification;    // 자격증정보

    @OneToOne
    @JoinColumn(name = "image_id")
    private Image image; // 문제내용 - 이미지

    @ManyToOne
    @JoinColumn(name = "paper_id")
    @JsonIgnore
    private Paper paper;

    private String comment; //해설

    private int score = 0;

    public Problem(ProblemForm form) {
        this.title = form.getTitle();
        this.round = form.getRound();
        this.subject = form.getSubject();
        this.problem = form.getProblem();
        this.comment = form.getComment();
        this.score = form.getScore();
    }

    public void update(Problem updateProblem) {
        this.title = updateProblem.getTitle();
        this.round = updateProblem.getRound();
        this.subject = updateProblem.getSubject();
        this.problem = updateProblem.getProblem();
        this.image = updateProblem.getImage();
        this.comment = updateProblem.getComment();
    }

    /**
     * 이미지 첨부
     * @param image
     */
    public void attachImage(Image image) {
        this.image = image;
    }

    public void appendedPaper(Paper paper) {
        this.paper = paper;
    }

    /**
     * 문제 채점 기능
     * 제출 답안과 등록된 정답을 비교해서 결과를 반환
     * @param submitAnswer - 제출 답안
     * @return
     */
    public abstract MarkingProblem mark(String submitAnswer);
}
