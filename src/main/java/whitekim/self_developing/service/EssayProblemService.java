package whitekim.self_developing.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import whitekim.self_developing.dto.response.MarkingProblem;
import whitekim.self_developing.model.Certification;
import whitekim.self_developing.model.ChoiceProblem;
import whitekim.self_developing.model.EssayProblem;
import whitekim.self_developing.repository.*;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EssayProblemService extends ProblemService<EssayProblem> {
    public EssayProblemService(EssayProblemRepository problemRepository, CertRepository certRepository, PaperRepository paperRepository) {
        super(problemRepository, certRepository, paperRepository);
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

    @Override
    public MarkingProblem markingProblem(Long problemId, String answer) {
        return super.markingProblem(problemId, answer);
    }

    @Override
    public EssayProblem searchRandomProblemByCertification(String certName) {
        Certification certification = certRepository.findByCertName(certName).orElseThrow(RuntimeException::new);
        EssayProblemRepository repository = (EssayProblemRepository) problemRepository;

        return repository.selectRandomProblemByCertification(certification);
    }
}
