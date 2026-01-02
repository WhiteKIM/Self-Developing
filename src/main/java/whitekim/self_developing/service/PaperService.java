package whitekim.self_developing.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
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
@Slf4j
@RequiredArgsConstructor
public class PaperService {
    private final PaperRepository paperRepository;
    private final PageRepository pageRepository;
    private final ProblemFactory problemFactory;
    private final ProblemRepoFactory problemRepoFactory;
    private final ProblemServiceFactory problemServiceFactory;
    private final VoteService voteService;
    private final ImageService imageService;

    /**
     * 시험지를 등록합니다.
     * @param paper
     */
    public Long registerPaper(Long pageId, PaperForm paper) {
        log.info("[PaperService] PageId : {}", pageId);

        Page page = pageRepository.findById(pageId).orElseThrow();
        Paper savePage = paperRepository.save(new Paper(paper));

        page.addPaper(savePage);

        return savePage.getId();
    }

    public List<Paper> getPaperList(Long pageId) {
        Optional<Page> optPage = pageRepository.findById(pageId);

        if(optPage.isEmpty()) {
            throw new NotFoundDataException("존재하지 않는 항목입니다.");
        }

        return optPage.get().getPaperList();
    }

    /**
     * 문제집에 문제 추가
     * @param paperId
     * @param problemList
     * @param uploadFiles
     */
    public void addProblem(Long paperId, List<ProblemForm> problemList, List<MultipartFile> uploadFiles) {
        Optional<Paper> optPaper = paperRepository.findById(paperId);
        int imageIndex = 0;

        if(optPaper.isEmpty()) {
            throw new NotFoundDataException("존재하지 않는 항목입니다.");
        }

        Paper paper = optPaper.get();

        log.info("[PaperService] ProblemList : {}", problemList.size());

        for (ProblemForm form : problemList) {
            String type = form.getProblemType();
            // 적절한 타입으로 생성해서 넣어주어야 함
            Problem problem = problemFactory.createProblem(form);
            ProblemRepository<? extends Problem> problemRepository = problemRepoFactory.createRepository(form.getProblemType());

            Image image = null;

             /*
              이미지 추가/변경 처리
              추가된 이미지 정보가 있다면 가져온 후 인덱스 증가
             */
            if (form.getImageMetaInfo().isHasImage())
                image = imageService.saveImage(uploadFiles.get(imageIndex++));

            problem.attachImage(image);

            log.info("[PaperService] Problem : {}", problem);

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

    /**
     * 문제집 내 문제 수정
     * @param paperId - 문제집 ID
     * @param problemList - 문제 정보
     * @param uploadFiles - 업로드된 이미지
     */
    public void updateProblem(Long paperId, List<ProblemForm> problemList, List<MultipartFile> uploadFiles) {
        Optional<Paper> optPaper = paperRepository.findById(paperId);
        int imageIndex = 0;

        if(optPaper.isEmpty()) {
            throw new NotFoundDataException("존재하지 않는 항목입니다.");
        }

        log.info("[PaperService] UploadImage : {}", uploadFiles.stream().toString());

        for (ProblemForm form : problemList) {
            String type = form.getProblemType();

            // 해당 문제를 찾아서 수정 처리를 해주어야 함
            ProblemService<? extends Problem> problemService = problemServiceFactory.createService(form.getProblemType());

            Image image = null;

            /*
              이미지 추가/변경 처리
              추가된 이미지 정보가 있다면 가져온 후 인덱스 증가
             */
            if (form.getImageMetaInfo().isHasImage())
                image = imageService.saveImage(uploadFiles.get(imageIndex++));

            switch (type) {
                case "CHOICE":
                    ChoiceProblemService choiceRepo = (ChoiceProblemService) problemService;
                    choiceRepo.updateProblem(form, image, paperId);
                    break;
                case "ESSAY":
                    EssayProblemService essayRepo = (EssayProblemService) problemService;
                    essayRepo.updateProblem(form, image, paperId);
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
