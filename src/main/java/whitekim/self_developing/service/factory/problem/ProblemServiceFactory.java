package whitekim.self_developing.service.factory.problem;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import whitekim.self_developing.model.ChoiceProblem;
import whitekim.self_developing.model.EssayProblem;
import whitekim.self_developing.model.Problem;
import whitekim.self_developing.service.ChoiceProblemService;
import whitekim.self_developing.service.EssayProblemService;
import whitekim.self_developing.service.ProblemService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ProblemServiceFactory {
    private final Map<Class<? extends Problem>, ProblemService<? extends Problem>> problemServiceMap = new HashMap<>();
    private final EssayProblemService essayProblemService;
    private final ChoiceProblemService choiceProblemService;

    @PostConstruct
    private void initFactory() {
        problemServiceMap.put(EssayProblem.class, essayProblemService);
        problemServiceMap.put(ChoiceProblem.class, choiceProblemService);
    }

    public ProblemService<? extends Problem> createService(Class<? extends Problem> type) {
        ProblemService<? extends Problem> problemService = problemServiceMap.get(type);

        if(problemService == null) {
            throw new RuntimeException("존재하지 않는 타입 정보입니다.");
        }

        return problemService;
    }

    public List<ProblemService<? extends Problem>> getService() {
        List<ProblemService<? extends Problem>> serviceList = new ArrayList<>();

        for(Map.Entry<Class<? extends Problem>, ProblemService<? extends Problem>> entry: problemServiceMap.entrySet()) {
            serviceList.add(entry.getValue());
        }

        return serviceList;
    }
}
