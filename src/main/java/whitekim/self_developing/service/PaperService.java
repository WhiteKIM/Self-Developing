package whitekim.self_developing.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import whitekim.self_developing.dto.request.SearchPaper;
import whitekim.self_developing.model.*;
import whitekim.self_developing.repository.CategoryRepository;
import whitekim.self_developing.repository.PageRepository;
import whitekim.self_developing.repository.PaperRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PaperService {
    private final PaperRepository paperRepository;
    private final PageRepository pageRepository;

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
     * @param pageId - 페이지 아이디
     * @return 검색된 문제 내역
     */
    public List<Paper> searchPaper(Long pageId) {
        Optional<Page> optionalPage = pageRepository.findById(pageId);

        if(optionalPage.isEmpty())
            throw new RuntimeException("Wrong Access");

        return optionalPage.get().getPaperList();
    }

    public Paper getPaperDetail(Long id) {
        Optional<Paper> optionalPaper = paperRepository.findById(id);

        if(optionalPaper.isEmpty())
            throw new RuntimeException("Not Exist Data");

        return optionalPaper.get();
    }
}
