package whitekim.self_developing.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@DiscriminatorValue("Choice")
public class ChoiceProblem extends Problem {
    private ProblemType type = ProblemType.CHOICE;
    @Builder.Default
    private List<String> suggest = new ArrayList<>();
    @Builder.Default
    private List<String> answer = new ArrayList<>();//중복답 처리가 필요한 경우도 있을 수 있음

    @Override
    public void update(Problem updateProblem) {
        if(!(updateProblem instanceof ChoiceProblem choiceProblem))
            throw new ClassCastException("형변환 불가능한 정보입니다.");

        super.update(updateProblem);
        this.suggest = choiceProblem.getSuggest();
        this.answer = choiceProblem.getAnswer();
    }
}
