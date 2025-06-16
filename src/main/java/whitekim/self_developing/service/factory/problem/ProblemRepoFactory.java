package whitekim.self_developing.service.factory.problem;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import whitekim.self_developing.model.ChoiceProblem;
import whitekim.self_developing.model.EssayProblem;
import whitekim.self_developing.model.Problem;
import whitekim.self_developing.repository.ChoiceProblemRepository;
import whitekim.self_developing.repository.EssayProblemRepository;
import whitekim.self_developing.repository.ProblemRepository;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class ProblemRepoFactory {
    private final Map<Class<? extends Problem>, ProblemRepository<? extends Problem>> problemRepoMap = new HashMap<>();
    private final EssayProblemRepository essayProblemRepository;
    private final ChoiceProblemRepository choiceProblemRepository;

    @PostConstruct
    public void initFactory() {
        problemRepoMap.put(EssayProblem.class, essayProblemRepository);
        problemRepoMap.put(ChoiceProblem.class, choiceProblemRepository);
    }

    /**
     * 문제 타입에 맞는 리포지토리 반환
     * @param type
     * @return
     */
    public ProblemRepository<? extends Problem> createRepository(String type) {
        ProblemRepository<? extends Problem> repository = problemRepoMap.get(ProblemRepoType.valueOf(type).getClazz());

        if(repository == null) {
            throw new RuntimeException("존재하지 않는 타입 정보입니다.");
        }

        return repository;
    }

}
