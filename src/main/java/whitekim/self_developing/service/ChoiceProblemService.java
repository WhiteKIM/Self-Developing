package whitekim.self_developing.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import whitekim.self_developing.dto.request.ProblemForm;
import whitekim.self_developing.dto.response.MarkingProblem;
import whitekim.self_developing.exception.NotFoundDataException;
import whitekim.self_developing.model.*;
import whitekim.self_developing.repository.CertRepository;
import whitekim.self_developing.repository.ChoiceProblemRepository;
import whitekim.self_developing.repository.PaperRepository;
import whitekim.self_developing.service.factory.problem.ProblemFactory;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ChoiceProblemService extends ProblemService<ChoiceProblem> {
    private final ProblemFactory problemFactory;

    public ChoiceProblemService(
            ChoiceProblemRepository problemRepository,
            CertRepository certRepository,
            PaperRepository paperRepository,
            VoteService voteService,
            ProblemFactory problemFactory
    ) {
        super(problemRepository, certRepository, paperRepository, voteService);
        this.problemFactory = problemFactory;
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
    public void updateProblem(ProblemForm form, Image image, Long paperId) {
        String status = form.getStatus();

        if(status.equals("U")) {
            // 해당 문제가 수정상태인 경우
            Optional<ChoiceProblem> optProblem = problemRepository.findById(form.getId());

            if(optProblem.isEmpty())
                throw new NotFoundDataException("존재하지 않는 항목입니다.");

            ChoiceProblem choiceProblem = optProblem.get();
            choiceProblem.update(form.toChoice());

            if((form.getImageFileName() == null || form.getImageFileName().isEmpty()) && image == null) {
                choiceProblem.attachImage(null);
            } else if(image != null) {
                choiceProblem.attachImage(image);
            }
        } else if(status.equals("D")){
            // 해당 문제가 삭제인 경우
            super.deleteProblem(form.getId());

            Paper paper = paperRepository.findById(paperId).orElseThrow();
            paper.deleteProblem(form.getId());
        } else {
            // 새로운 문제 추가
            Problem problem = problemFactory.createProblem(form);
            ChoiceProblem saveProblem = problemRepository.save((ChoiceProblem) problem);

            Paper paper = paperRepository.findById(paperId).orElseThrow();
            paper.addProblem(saveProblem);
        }
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

    @Override
    public MarkingProblem markingProblem(Long problemId, String answer) {
        return super.markingProblem(problemId, answer);
    }

    /**
     * 해당 자격증 카테고리 내에서 문제를 랜덤하게 선택한다.
     * @param certName
     * @return
     */
    @Override
    public ChoiceProblem searchRandomProblemByCertification(String certName) {
        Certification certification = certRepository.findByCertName(certName).orElseThrow(RuntimeException::new);
        ChoiceProblemRepository repository = (ChoiceProblemRepository) problemRepository;

        return repository.selectRandomProblemByCertification(certification);
    }
}
