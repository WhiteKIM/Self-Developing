package whitekim.self_developing.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import whitekim.self_developing.dto.request.ProblemForm;
import whitekim.self_developing.dto.response.MarkingProblem;
import whitekim.self_developing.exception.NotExistProblemException;
import whitekim.self_developing.model.Certification;
import whitekim.self_developing.model.Image;
import whitekim.self_developing.model.Paper;
import whitekim.self_developing.model.problem.Answer;
import whitekim.self_developing.model.problem.Problem;
import whitekim.self_developing.repository.CertRepository;
import whitekim.self_developing.repository.PaperRepository;
import whitekim.self_developing.repository.ProblemRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProblemService {
    private final ProblemRepository problemRepository;
    private final CertRepository certRepository;
    private final PaperRepository paperRepository;
    private final VoteService voteService;

    /**
     * 전체 문제 조회 로직
     */
    public List<Problem> getProblemList() {
        return problemRepository.findAll();
    }

    /**
     * 다건 문제 조회
     * 문제집 내에 속한 모든 문제를 가져온다.
     * @param paperId
     * @return
     */
    public List<Problem> findAllByPaper(Long paperId) {
        Optional<Paper> optionalPaper = paperRepository.findById(paperId);

        if(optionalPaper.isEmpty()) {
            throw new RuntimeException();
        }

        Paper paper = optionalPaper.get();
        return (List<Problem>) paper.getProblemList();
    }

    /**
     * 단건 문제 조회
     * @param id - 조회할 문제 키
     * @return 조회한 문제
     */
    public Optional<Problem> getProblem(Long id) {
        return problemRepository.findById(id);
    }

    /**
     * 무한 문제 풀이
     * 랜덤으로 해당 자격증 내에 등록된 문제 중에서 하나를 랜덤하게 가져옴
     * @param certName - 지격증명
     * @return - 랜덤한 문제
     */
    public Problem getRandomProblem(String certName) {
        Optional<Certification> optCert = certRepository.findByCertName(certName);

        if(optCert.isEmpty())
            throw new RuntimeException();

        Long size = problemRepository.countByCertification(optCert.get());

        int index = (int) (Math.random() * size);
        PageRequest pageRequest = PageRequest.of(index, 1);
        Page<Problem> problemPage = problemRepository.findAllByCertification(optCert.get(), pageRequest);

        return problemPage.getContent().getFirst();
    }

    /**
     * 단건 문제 등록
     */
    public void addProblem(Problem problem) {
        problemRepository.save(problem);
    }

    /**
     * 다건 문제 등록
     * @param problemList - 등록한 다건 문제
     */
    public void addProblemList(List<Problem> problemList) {
        problemRepository.saveAll(problemList);
    }

    /**
     * 단건 문제 수정
     * @param problem - 수정할 문제 내용
     */
    public void updateProblem(Problem problem) {
        Optional<Problem> optProblem = problemRepository.findById(problem.getId());

        if(optProblem.isEmpty()) {
            throw new RuntimeException("존재하지 않는 문제입니다.");
        }

        Problem Problem = optProblem.get();
        Problem.update(problem);
    }

    /**
     * 단건 문제 수정
     * @param form - 수정할 문제 정보
     * @param image - 첨부이미지
     * @param paperId - 해당 문제집 ID
     */
    public void updateProblem(ProblemForm form, Image image, Long paperId) {

    }

    /**
     * 다건 문제 수정
     * @param problemList - 수정할 문제 내용
     */
    public void updateProblemList(List<Problem> problemList) {
        for(Problem problem : problemList) {
            updateProblem(problem);
        }
    }

    /**
     * 단건 문제 제거s
     * @param id - 삭제할 문제 식별키
     */
    public void deleteProblem(Long id) {
        problemRepository.deleteById(id);
    }

    /**
     * 다건 문제 삭제
     * @param idList - 삭제할 문제 식별키 리스트
     */
    public void deleteProblemList(List<Long> idList) {
        for(Long id : idList) {
            problemRepository.deleteById(id);
        }
    }

    /**
     * 제출 답안 채점
     */
    public MarkingProblem markingProblem(Long problemId, Answer answer) {
        Problem problem = problemRepository.findById(problemId).orElseThrow(NotExistProblemException::new);

        return problem.mark(answer);
    }

    /**
     * 추상 메소드
     * 하위 자식 클래스 중에서 각각 랜덤하게 값을 하나씩 조회한다.
     * @NOTICE : PostgreSQL에서만 지원합니다.
     */
    public Problem searchRandomProblemByCertification(String certName) {
        return null;
    }

    /**
     * 문제집 추천 메소드
     * 사용자가 입력한 추천 정보를 반영한다.
     */
    public void addVote(Long id, String type) {
        Problem Problem = problemRepository.findById(id).orElseThrow();
        Problem.addVote(voteService.addVote(type));
    }

    public void removeVote() {

    }
}
