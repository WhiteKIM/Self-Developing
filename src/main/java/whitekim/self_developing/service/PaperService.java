package whitekim.self_developing.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import whitekim.self_developing.model.Paper;
import whitekim.self_developing.model.Problem;
import whitekim.self_developing.repository.PaperRepository;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PaperService {
    private final PaperRepository paperRepository;
    private final ProblemService<Problem> problemService;

    /**
     * 시험지를 등록합니다.
     * @param paper
     */
    public void registerPaper(Paper paper) {
        paperRepository.save(paper);
    }

    /**
     * 시험지에 문제를 추가
     */
    public void addProblemIntoPaper(Long id, Problem problem) {
        Optional<Paper> optPaper = paperRepository.findById(id);

        if(optPaper.isEmpty())
            throw new RuntimeException();

        Paper paper = optPaper.get();
        paper.addProblem(problem);
    }
}
