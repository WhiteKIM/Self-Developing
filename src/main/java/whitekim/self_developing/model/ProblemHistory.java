package whitekim.self_developing.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import whitekim.self_developing.model.problem.ProblemEntity;

@Entity
@AllArgsConstructor
@Getter
public class ProblemHistory extends BaseEntity {
    // 정답여부
    boolean isCorrect;

    @ManyToOne
    @JoinColumn(name = "problem_id")
    @JsonIgnore
    private ProblemEntity problem;

    @ManyToOne
    @JoinColumn(name = "member_id")
    @JsonIgnore
    private Member member;
}
