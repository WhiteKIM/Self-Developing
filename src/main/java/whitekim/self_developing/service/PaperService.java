package whitekim.self_developing.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import whitekim.self_developing.dto.request.PaperForm;
import whitekim.self_developing.dto.request.ProblemForm;
import whitekim.self_developing.dto.request.SubmitAnswer;
import whitekim.self_developing.dto.response.MarkingPaper;
import whitekim.self_developing.dto.response.MarkingProblem;
import whitekim.self_developing.exception.NotExistPaperException;
import whitekim.self_developing.exception.NotFoundDataException;
import whitekim.self_developing.model.Image;
import whitekim.self_developing.model.Page;
import whitekim.self_developing.model.Paper;
import whitekim.self_developing.model.problem.Answer;
import whitekim.self_developing.model.problem.Problem;
import whitekim.self_developing.repository.PageRepository;
import whitekim.self_developing.repository.PaperRepository;
import whitekim.self_developing.repository.ProblemRepository;

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
    private final ProblemRepository problemRepository;
    private final ProblemService problemService;
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
            Problem problem = Problem.toEntity(form);
            Image image = null;

             /*
              이미지 추가/변경 처리
              추가된 이미지 정보가 있다면 가져온 후 인덱스 증가
             */
            if (form.getImageMetaInfo().isHasImage())
                image = imageService.saveImage(uploadFiles.get(imageIndex++));

            problem.attachImage(image);

            log.info("[PaperService] Problem : {}", problem);

            Problem saveProblem = problemRepository.save(problem);
            paper.addProblem(saveProblem);
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

            // 해당 문제를 찾아서 수정 처리를 해주어야 함
            Image image = null;

            /*
              이미지 추가/변경 처리
              추가된 이미지 정보가 있다면 가져온 후 인덱스 증가
             */
            if (form.getImageMetaInfo().isHasImage())
                image = imageService.saveImage(uploadFiles.get(imageIndex++));

            problemService.updateProblem(form, image, paperId);
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
    public MarkingPaper markingPaper(Long paperId, List<SubmitAnswer> answerList) {
        Paper paper = paperRepository.findById(paperId).orElseThrow(NotExistPaperException::new);
        List<Problem> problemList = paper.getProblemList();
        List<MarkingProblem> markingProblemList = new ArrayList<>();

        int rightCount = 0;
        int wrongCount = 0;
        int score = 0;

        for(int i = 0; i < problemList.size(); i++) {
            Problem problem = problemList.get(i);
            MarkingProblem markResult = problemService.markingProblem(problem.getId(), Answer.toEntity(answerList.get(i)));

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


    public List<Paper> getPaperList() {
        return paperRepository.findAll();
    }
}
