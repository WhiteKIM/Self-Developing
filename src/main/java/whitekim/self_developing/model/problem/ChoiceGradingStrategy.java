package whitekim.self_developing.model.problem;

import lombok.Getter;
import org.springframework.stereotype.Component;
import whitekim.self_developing.model.enumerate.ProblemType;

import java.util.List;

@Getter
@Component("CHOICE")
public class ChoiceGradingStrategy implements GradingStrategy{
    private final List<String> keyword;   // 제시어

    public ChoiceGradingStrategy(List<String> keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean grade(Answer correctAnswer, Answer userAnswer) {
        return false;
    }

    @Override
    public ProblemType getType() {
        return ProblemType.CHOICE;
    }
}
