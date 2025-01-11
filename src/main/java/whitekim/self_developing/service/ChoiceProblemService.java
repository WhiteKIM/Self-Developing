package whitekim.self_developing.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import whitekim.self_developing.model.ChoiceProblem;
import whitekim.self_developing.repository.ProblemRepository;

@Service
@Transactional
public class ChoiceProblemService extends ProblemService<ChoiceProblem> {
    public ChoiceProblemService(ProblemRepository<ChoiceProblem> problemRepository) {
        super(problemRepository);
    }
}
