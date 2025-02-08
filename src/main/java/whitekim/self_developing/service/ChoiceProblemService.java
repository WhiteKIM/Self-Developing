package whitekim.self_developing.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import whitekim.self_developing.model.ChoiceProblem;
import whitekim.self_developing.repository.CertRepository;
import whitekim.self_developing.repository.ProblemRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ChoiceProblemService extends ProblemService<ChoiceProblem> {
    public ChoiceProblemService(ProblemRepository<ChoiceProblem> problemRepository, CertRepository certRepository) {
        super(problemRepository, certRepository);
    }

    @Override
    public Optional<ChoiceProblem> getProblem(Long id) {
        return super.getProblem(id);
    }

    @Override
    public void addProblem(ChoiceProblem problem) {
        super.addProblem(problem);
    }

    @Override
    public void addProblemList(List<ChoiceProblem> problemList) {
        super.addProblemList(problemList);
    }

    @Override
    public void updateProblem(ChoiceProblem problem) {
        super.updateProblem(problem);
    }

    @Override
    public void updateProblemList(List<ChoiceProblem> problemList) {
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
