package whitekim.self_developing.model.problem;

import org.springframework.stereotype.Component;
import whitekim.self_developing.model.enumerate.ProblemType;

/**
 * 주관식 문제 채점
 */
@Component("SUBJECT")
public class SubjectGradingStrategy implements GradingStrategy{
    @Override
    public boolean grade(Answer correctAnswer, Answer userAnswer) {
        return correctAnswer.getContent().equals(userAnswer.getContent());
    }

    @Override
    public ProblemType getType() {
        return ProblemType.SUBJECT;
    }
}
