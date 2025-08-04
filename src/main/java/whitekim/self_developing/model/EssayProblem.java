package whitekim.self_developing.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;
import whitekim.self_developing.dto.request.ProblemForm;
import whitekim.self_developing.dto.response.MarkingProblem;

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
        this.answer = form.getAnswer().getFirst();
    }

    @Override
    public void update(Problem updateProblem) {
        if(!(updateProblem instanceof EssayProblem essayProblem))
            throw new ClassCastException("형변환 불가능한 정보입니다.");

        super.update(updateProblem);
        this.answer = essayProblem.getAnswer();
    }

    /**
     * @param submitAnswer - 제출 답안
     * @return
     */
    @Override
    public MarkingProblem mark(String submitAnswer) {
        Long imageId = super.getImage() == null ? null : super.getImage().getId();

        if(answer.equals(submitAnswer)) {
            return new MarkingProblem(super.getId(), true, super.getScore(),  ProblemType.ESSAY.toString(), super.getProblem(), imageId, submitAnswer, answer, getComment());
        } else {
            return new MarkingProblem(super.getId(),  false, 0, ProblemType.ESSAY.toString(), super.getProblem(), imageId, submitAnswer, answer, getComment());
        }
    }
}
