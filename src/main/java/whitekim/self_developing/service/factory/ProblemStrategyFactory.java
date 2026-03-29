package whitekim.self_developing.service.factory;

import org.springframework.stereotype.Component;
import whitekim.self_developing.model.problem.GradingStrategy;

import java.util.Map;

@Component
public class ProblemStrategyFactory {
    private final Map<String, GradingStrategy> gradingStrategyMap;

    public ProblemStrategyFactory(Map<String, GradingStrategy> gradingStrategyMap) {
        this.gradingStrategyMap = gradingStrategyMap;
    }

    public GradingStrategy createStrategy(String strategyType) {
        return gradingStrategyMap.get(strategyType);
    }
}
