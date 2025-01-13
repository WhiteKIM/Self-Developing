package whitekim.self_developing.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import whitekim.self_developing.model.Problem;
import whitekim.self_developing.repository.ProblemRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public abstract class ProblemService<T extends Problem> {
    private final ProblemRepository<T> problemRepository;

    public ProblemService(ProblemRepository<T> problemRepository) {
        this.problemRepository = problemRepository;
    }

    /**
     * 단건 문제 조회
     * @param id - 조회할 문제 키
     * @return 조회한 문제
     */
    public Optional<T> getProblem(Long id) {
        return problemRepository.findById(id);
    }

    /**
     * 단건 문제 등록
     */
    public void addProblem(T problem) {
        problemRepository.save(problem);
    }

    /**
     * 다건 문제 등록
     * @param problemList - 등록한 다건 문제
     */
    public void addProblemList(List<T> problemList) {
        problemRepository.saveAll(problemList);
    }

    /**
     * 단건 문제 수정
     * @param problem - 수정할 문제 내용
     */
    public void updateProblem(T problem) {
        Optional<T> optProblem = problemRepository.findById(problem.getId());

        if(optProblem.isEmpty()) {
            throw new RuntimeException("존재하지 않는 문제입니다.");
        }

        T t = optProblem.get();
        t.update(problem);
    }

    /**
     * 다건 문제 수정
     * @param problemList - 수정할 문제 내용
     */
    public void updateProblemList(List<T> problemList) {
        for(T problem : problemList) {
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
}
