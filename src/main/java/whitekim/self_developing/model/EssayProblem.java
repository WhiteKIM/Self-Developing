package whitekim.self_developing.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;
import whitekim.self_developing.dto.request.ProblemForm;

@Entity
@Getter
@DiscriminatorValue("Essay")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class EssayProblem extends Problem{
    private ProblemType type;   // 문제 타입
    private String answer;      //정답

    public EssayProblem(ProblemForm form) {
        super(form);
        this.type = ProblemType.ESSAY;
        this.answer = form.getAnswer().get(0);
    }

    @Override
    public void update(Problem updateProblem) {
        if(!(updateProblem instanceof EssayProblem essayProblem))
            throw new ClassCastException("형변환 불가능한 정보입니다.");

        super.update(updateProblem);
        this.answer = essayProblem.getAnswer();
    }
}
