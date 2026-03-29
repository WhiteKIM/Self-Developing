package whitekim.self_developing.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import whitekim.self_developing.model.problem.ProblemEntity;

@Entity
@AllArgsConstructor
public class ProblemHistory extends BaseEntity {
    // 정답여부
    boolean isCorrect;

    @ManyToOne
    @JoinColumn(name = "problem_id")
    private ProblemEntity problem;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
}
