package whitekim.self_developing.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import whitekim.self_developing.dto.request.SearchPaper;
import whitekim.self_developing.model.Certification;
import whitekim.self_developing.model.Paper;
import whitekim.self_developing.model.PaperType;
import whitekim.self_developing.model.Problem;
import whitekim.self_developing.repository.PaperRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PaperService {
    private final PaperRepository paperRepository;
    private final ProblemService<Problem> problemService;
    private final CertService certService;

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


    /**
     * 선택한 자격증에 대한 문제를 검색
     * @param searchPaper - 검색조건
     * @return 검색된 문제 내역
     */
    public List<Paper> searchPaper(SearchPaper searchPaper) {
        Certification cert = certService.findByCertificationName(searchPaper.getCertificationName());
        PaperType type = PaperType.valueOf(searchPaper.getType());

        return paperRepository.findByCertificationAndPaperType(cert, type);
    }
}
