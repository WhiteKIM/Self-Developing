package whitekim.self_developing.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import whitekim.self_developing.dto.request.ProblemForm;
import whitekim.self_developing.dto.response.MarkingProblem;
import whitekim.self_developing.exception.NotFoundDataException;
import whitekim.self_developing.model.Certification;
import whitekim.self_developing.model.EssayProblem;
import whitekim.self_developing.model.Image;
import whitekim.self_developing.repository.CertRepository;
import whitekim.self_developing.repository.EssayProblemRepository;
import whitekim.self_developing.repository.PaperRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class EssayProblemService extends ProblemService<EssayProblem> {
    public EssayProblemService(EssayProblemRepository problemRepository, CertRepository certRepository, PaperRepository paperRepository, VoteService voteService) {
        super(problemRepository, certRepository, paperRepository, voteService);
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
    public void updateProblem(ProblemForm form, Image image) {
        String status = form.getStatus();

        if(status.equals("U")) {
            Optional<EssayProblem> optProblem = problemRepository.findById(form.getId());

            if(optProblem.isEmpty())
                throw new NotFoundDataException("존재하지 않는 항목입니다.");

            EssayProblem essayProblem = optProblem.get();
            essayProblem.update(form.toEssay());

            if(form.getImageFileName().isEmpty() && image == null) {
                essayProblem.attachImage(null);
            } else if(image != null) {
                essayProblem.attachImage(image);
            }

        } else {
            super.deleteProblem(form.getId());
        }
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
