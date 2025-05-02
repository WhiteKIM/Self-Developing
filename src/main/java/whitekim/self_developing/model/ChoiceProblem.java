package whitekim.self_developing.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;
import whitekim.self_developing.dto.request.ProblemForm;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@DiscriminatorValue("Choice")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ChoiceProblem extends Problem {
    private ProblemType type = ProblemType.CHOICE;
    private List<String> suggest = new ArrayList<>();
    private List<String> answer = new ArrayList<>();//중복답 처리가 필요한 경우도 있을 수 있음

    public ChoiceProblem(ProblemForm form) {
        super(form);
        this.type = ProblemType.CHOICE;
        this.suggest = form.getSuggest();
    }

    @Override
    public void update(Problem updateProblem) {
        if(!(updateProblem instanceof ChoiceProblem choiceProblem))
            throw new ClassCastException("형변환 불가능한 정보입니다.");

        super.update(updateProblem);
        this.suggest = choiceProblem.getSuggest();
        this.answer = choiceProblem.getAnswer();
    }
}
