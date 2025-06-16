package whitekim.self_developing.service.factory.problem;

import whitekim.self_developing.model.ChoiceProblem;
import whitekim.self_developing.model.EssayProblem;
import whitekim.self_developing.model.Problem;

public enum ProblemRepoType {
    CHOICE(ChoiceProblem.class),
    ESSAY(EssayProblem.class);

    private final Class<? extends Problem> clazz;

    ProblemRepoType(Class<? extends Problem> clazz) {
        this.clazz = clazz;
    }

    public Class<? extends Problem> getClazz() {
        return clazz;
    }
}
