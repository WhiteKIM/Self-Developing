package whitekim.self_developing;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import whitekim.self_developing.model.Category;
import whitekim.self_developing.model.Member;
import whitekim.self_developing.service.CategoryService;
import whitekim.self_developing.service.MemberService;

@Component
@RequiredArgsConstructor
public class TestConstruct {
    private final MemberService memberService;
    private final CategoryService categoryService;


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
    }
}
