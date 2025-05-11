package whitekim.self_developing;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import whitekim.self_developing.dto.request.CertForm;
import whitekim.self_developing.dto.request.PageForm;
import whitekim.self_developing.dto.request.PaperForm;
import whitekim.self_developing.dto.request.ProblemForm;
import whitekim.self_developing.model.Category;
import whitekim.self_developing.model.Member;
import whitekim.self_developing.service.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 테스트 데이터 세팅
 */
@Component
@RequiredArgsConstructor
public class TestConstruct {
    private final MemberService memberService;
    private final CategoryService categoryService;
    private final CertService certService;
    private final PageService pageService;
    private final PaperService paperService;
    
    
    @PostConstruct
    void init() {
        // 사용자 회원가입 및 등록
        Member member = Member.builder()
                .username("test")
                .password("test")
                .email("test@test.com")
                .build();

        memberService.registerMember(member);

        // 카테고리 생성
        categoryService.saveCategory(new Category("test"));

        // 자격증 정보 생성
        CertForm certForm = new CertForm();
        certForm.setCertName("test");
        certForm.setExamTime(60L);
        certForm.setSubjectCount(60L);

        certService.saveCertification(certForm);

        // 페이지 생성
        PageForm pageForm = new PageForm();
        pageForm.setPageType("CERTIFICATION");
        pageForm.setCertification("test");
        pageForm.setCategory("test");

        pageService.registerPage(pageForm);

        // 문제집 생성
        PaperForm paperForm = new PaperForm();
        paperForm.setTitle("test");
        paperForm.setType("MOCK");
        paperService.registerPaper(1L, paperForm);

        // 문제 등록
        ProblemForm problemForm = new ProblemForm();
        problemForm.setProblemType("ESSAY");
        problemForm.setTitle("test");
        problemForm.setRound("4");
        problemForm.setSubject("test");
        problemForm.setProblem("test");
        problemForm.setSuggest(List.of("test"));
        problemForm.setCategoryName("test");
        problemForm.setAnswer(List.of("test"));
        problemForm.setComment("test");

        paperService.addProblem(1L, new ArrayList<>());
    }
}
