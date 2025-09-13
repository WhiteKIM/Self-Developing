package whitekim.self_developing.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import whitekim.self_developing.dto.request.PaperForm;
import whitekim.self_developing.dto.request.ProblemForm;
import whitekim.self_developing.dto.response.MarkingPaper;
import whitekim.self_developing.dto.response.MarkingProblem;
import whitekim.self_developing.exception.NotExistPaperException;
import whitekim.self_developing.exception.NotFoundDataException;
import whitekim.self_developing.model.*;
import whitekim.self_developing.repository.*;
import whitekim.self_developing.service.factory.problem.ProblemFactory;
import whitekim.self_developing.service.factory.problem.ProblemRepoFactory;
import whitekim.self_developing.service.factory.problem.ProblemServiceFactory;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PaperService {
    private final PaperRepository paperRepository;
    private final PageRepository pageRepository;
    private final ProblemFactory problemFactory;
    private final ProblemRepoFactory problemRepoFactory;
    private final ProblemServiceFactory problemServiceFactory;
    private final VoteService voteService;

    /**
     * 시험지를 등록합니다.
     * @param paper
     */
    public void registerPaper(Long pageId, PaperForm paper) {
        Page page = pageRepository.findById(pageId).orElseThrow();
        Paper savePage = paperRepository.save(new Paper(paper));

        page.addPaper(savePage);
    }

    public List<Paper> getPaperList(Long pageId) {
        Optional<Page> optPage = pageRepository.findById(pageId);

        if(optPage.isEmpty()) {
            throw new NotFoundDataException("존재하지 않는 항목입니다.");
        }

        return optPage.get().getPaperList();
    }

    public void addProblem(Long paperId, List<ProblemForm> problemList) {
        Optional<Paper> optPaper = paperRepository.findById(paperId);

        if(optPaper.isEmpty()) {
            throw new NotFoundDataException("존재하지 않는 항목입니다.");
        }

        Paper paper = optPaper.get();

        for(ProblemForm form : problemList) {
            String type = form.getProblemType();
            // 적절한 타입으로 생성해서 넣어주어야 함
            Problem problem = problemFactory.createProblem(form);
            ProblemRepository<? extends Problem> problemRepository = problemRepoFactory.createRepository(form.getProblemType());

            switch (type) {
                case "CHOICE":
                    ChoiceProblemRepository choiceRepo = (ChoiceProblemRepository) problemRepository;
                    ChoiceProblem choiceProblem = choiceRepo.save((ChoiceProblem) problem);
                    paper.addProblem(choiceProblem);
                    break;
                case "ESSAY":
                    EssayProblemRepository essayRepo = (EssayProblemRepository) problemRepository;
                    EssayProblem essayProblem = essayRepo.save((EssayProblem) problem);
                    paper.addProblem(essayProblem);
                    break;
                default:
                    throw new IllegalArgumentException("존재하지 않거나 잘못된 타입이 입력되었습니다.");
            }
        }
    }

    public Paper getPaperDetail(Long id) {
        Optional<Paper> optionalPaper = paperRepository.findById(id);

        if(optionalPaper.isEmpty())
            throw new RuntimeException("Not Exist Data");

        return optionalPaper.get();
    }

    /**
     * 문제지 채점 수행
     */
    public MarkingPaper markingPaper(Long paperId, List<String> answerList) {
        Paper paper = paperRepository.findById(paperId).orElseThrow(NotExistPaperException::new);
        List<Problem> problemList = paper.getProblemList();
        List<MarkingProblem> markingProblemList = new ArrayList<>();

        int rightCount = 0;
        int wrongCount = 0;
        int score = 0;

        for(int i = 0; i < problemList.size(); i++) {
            Problem problem = problemList.get(i);
            ProblemService<? extends Problem> service = problemServiceFactory.createService(problem.getClass());
            MarkingProblem markResult = service.markingProblem(problem.getId(), answerList.get(i));

            if (markResult.isCorrect()) {
                rightCount += 1;
                score+= markResult.score();
            } else {
                wrongCount += 1;
            }

            markingProblemList.add(markResult);
        }

        return new MarkingPaper(paper.getTitle(), paper.getTitle(), score, rightCount, wrongCount, LocalDateTime.now(), markingProblemList);
    }

    public void addVote(Long paperId, String type) {
        Paper paper = paperRepository.findById(paperId).orElseThrow(NotExistPaperException::new);
        paper.addVote(voteService.addVote(type));
    }

    public void removeVote(Long paperId) {
        Paper paper = paperRepository.findById(paperId).orElseThrow(NotExistPaperException::new);
        //
    }
}
