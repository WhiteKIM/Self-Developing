package whitekim.self_developing.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import whitekim.self_developing.model.EssayProblem;
import whitekim.self_developing.repository.CertRepository;
import whitekim.self_developing.repository.ProblemRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EssayProblemService extends ProblemService<EssayProblem> {
    public EssayProblemService(ProblemRepository<EssayProblem> problemRepository, CertRepository certRepository) {
        super(problemRepository, certRepository);
    }

    @Override
    public Optional<EssayProblem> getProblem(Long id) {
        return super.getProblem(id);
    }

    @Override
    public void addProblem(EssayProblem problem) {
        super.addProblem(problem);
    }

    @Override
    public void addProblemList(List<EssayProblem> problemList) {
        super.addProblemList(problemList);
    }

    @Override
    public void updateProblem(EssayProblem problem) {
        super.updateProblem(problem);
    }

    @Override
    public void updateProblemList(List<EssayProblem> problemList) {
        super.updateProblemList(problemList);
    }

    @Override
    public void deleteProblem(Long id) {
        super.deleteProblem(id);
    }

    @Override
    public void deleteProblemList(List<Long> idList) {
        super.deleteProblemList(idList);
    }
}
