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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PaperService {
    private final PaperRepository paperRepository;
    private final PageRepository pageRepository;
    private final ImageRepository imageRepository;
    private final ProblemFactory problemFactory;
    private final ProblemRepoFactory problemRepoFactory;
    private final ChoiceProblemRepository choiceProblemRepository;
    private final EssayProblemRepository essayProblemRepository;

    /**
     * 시험지를 등록합니다.
     * @param paper
     */
    public void registerPaper(PaperForm paper) {
        paperRepository.save(new Paper(paper));
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

            switch (type) {
                case "CHOICE":
                    ChoiceProblem choiceProblem = choiceProblemRepository.save((ChoiceProblem) problem);
                    paper.addProblem(choiceProblem);
                    break;
                case "ESSAY":
                    EssayProblem essayProblem = essayProblemRepository.save((EssayProblem) problem);
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
