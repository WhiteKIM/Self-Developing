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

/**
 * 문제 서비스에 대한 팩토리 기능 제공
 * Problem은 추상 클래스로 필요시 해당 클래스 확장하여 개발을 진행하는데
 * 이 경우 각각의 자식 클래스에 대한 자식 서비스가 생성되어 사용 시에 어려움이 있음
 * 이를 해당 팩토리를 사용하여 타입에 맞는 기능을 제공할 수 있도록 구현하였음
 */
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

    /**
     * 주어진 문제 타입에 맞는 적절한 서비스 클래스를 반환
     * @param type - 문제 클래스
     * @return - 구체 문제 서비스
     */
    public ProblemService<? extends Problem> createService(Class<? extends Problem> type) {
        ProblemService<? extends Problem> problemService = problemServiceMap.get(type);

        if(problemService == null) {
            throw new RuntimeException("존재하지 않는 타입 정보입니다.");
        }

        return problemService;
    }

    /**
     * 주어진 문제 타입에 맞는 적절한 서비스 클래스를 반환
     * @param type - 문제 클래스
     * @return - 구체 문제 서비스
     */
    public ProblemService<? extends Problem> createService(String type) {
        ProblemService<? extends Problem> problemService = problemServiceMap.get(ProblemRepoType.valueOf(type).getClazz());

        if(problemService == null) {
            throw new RuntimeException("존재하지 않는 타입 정보입니다.");
        }

        return problemService;
    }

    /**
     * 현재 팩토리에 등록된 전체 서비스 클래스에 대한 리스트를 제공
     * @return - 구체 문제서비스 클래스
     */
    public List<ProblemService<? extends Problem>> getService() {
        List<ProblemService<? extends Problem>> serviceList = new ArrayList<>();

        for(Map.Entry<Class<? extends Problem>, ProblemService<? extends Problem>> entry: problemServiceMap.entrySet()) {
            serviceList.add(entry.getValue());
        }

        return serviceList;
    }
}
