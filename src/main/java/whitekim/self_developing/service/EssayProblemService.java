package whitekim.self_developing.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import whitekim.self_developing.model.EssayProblem;
import whitekim.self_developing.repository.ProblemRepository;

@Service
@Transactional
public class EssayProblemService extends ProblemService<EssayProblem> {
    public EssayProblemService(ProblemRepository<EssayProblem> problemRepository) {
        super(problemRepository);
    }
}
