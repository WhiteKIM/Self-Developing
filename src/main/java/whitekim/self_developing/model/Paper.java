package whitekim.self_developing.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * 여러 문제가 등록된 문제지 or 시험지
 *
 */
@Entity
@Getter
public class Paper extends BaseEntity {
    private String title;   // 문제지 제목
    private Long timeLimit; // 제한시간 => 해당값이 존재하면 시간제한 문제집
    private PaperType type;

    @OneToOne
    @JoinColumn(name = "certification_id")
    private Certification certification;

    @OneToMany(mappedBy = "problem")
    private List<Problem> problemList = new ArrayList<>();

    public void addProblem(Problem problem) {
        problemList.add(problem);
        problem.appendedPaper(this);
    }
}
