package whitekim.self_developing.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DiscriminatorValue("Essay")
public class EssayProblem extends Problem{
    @Builder.Default
    private ProblemType type = ProblemType.ESSAY;
    private String answer;  //정답

    @Override
    public void update(Problem updateProblem) {
        if(!(updateProblem instanceof EssayProblem essayProblem))
            throw new ClassCastException("형변환 불가능한 정보입니다.");

        super.update(updateProblem);
        this.answer = essayProblem.getAnswer();
    }
}
