package whitekim.self_developing.model.problem;

import whitekim.self_developing.model.enumerate.ProblemType;

/**
 * 주관식 문제 채점
 */
public class SubjectGradingStrategy implements GradingStrategy{
    @Override
    public boolean grade(Answer correctAnswer, Answer userAnswer) {
        return false;
    }

    @Override
    public ProblemType getType() {
        return ProblemType.SUBJECT;
    }
}
