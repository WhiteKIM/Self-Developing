package whitekim.self_developing.model.problem;

import whitekim.self_developing.model.enumerate.ProblemType;

/**
 * 문제 채점 전략 인터페이스
 */
public interface GradingStrategy {
    boolean grade(Answer correctAnswer, Answer userAnswer);
    ProblemType getType();
}
