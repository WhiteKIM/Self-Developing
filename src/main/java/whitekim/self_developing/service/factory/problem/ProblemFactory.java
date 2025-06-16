package whitekim.self_developing.service.factory.problem;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import whitekim.self_developing.dto.request.ProblemForm;
import whitekim.self_developing.model.ChoiceProblem;
import whitekim.self_developing.model.EssayProblem;
import whitekim.self_developing.model.Problem;
import whitekim.self_developing.model.ProblemType;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class ProblemFactory {
    private final Map<String, Function<ProblemForm, ? extends Problem>> problemMap = new HashMap<>();

    @PostConstruct
    public void initFactory() {
        problemMap.put(ProblemType.CHOICE.toString(), ChoiceProblem::new);
        problemMap.put(ProblemType.ESSAY.toString(), EssayProblem::new);
    }

    /**
     * 팩토리 메소드
     * 주어진 문제타입에 맞는 문제를 생성해서 반환
     * @param form
     * @return
     */
    public Problem createProblem(ProblemForm form) {
        Function<ProblemForm, ? extends Problem> problemCreator = problemMap.get(form.getProblemType());

        if(problemCreator == null) {
            throw new RuntimeException("존재하지 않는 타입 정보입니다.");
        }
        
        return problemCreator.apply(form);
    }
}
