package whitekim.self_developing.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import whitekim.self_developing.dto.request.PaperForm;
import whitekim.self_developing.dto.request.ProblemForm;
import whitekim.self_developing.exception.NotFoundDataException;
import whitekim.self_developing.model.*;
import whitekim.self_developing.repository.*;
import whitekim.self_developing.service.factory.ProblemFactory;
import whitekim.self_developing.service.factory.ProblemRepoFactory;

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
}
