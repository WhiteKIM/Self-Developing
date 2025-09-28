package whitekim.self_developing.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import whitekim.self_developing.model.ProblemHistory;
import whitekim.self_developing.repository.ProblemHistoryRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class ProblemHistoryService {
    private final ProblemHistoryRepository historyRepository;

    public void createProblemHistory(ProblemHistory history) {
        historyRepository.save(history);
    }
}
