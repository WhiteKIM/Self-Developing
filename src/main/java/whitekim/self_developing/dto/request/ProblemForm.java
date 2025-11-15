package whitekim.self_developing.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;
import whitekim.self_developing.model.ChoiceProblem;
import whitekim.self_developing.model.Problem;

import java.util.List;

/**
 * @author whitekim
 * 문제 생성 시 받을 입력에 대한 DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProblemForm {
    @NotBlank
    private String problemType;     // 문제 타입
    @NotBlank
    private String title;           // 제목
    private String round;           // 회차
    private String subject;         // 과목정보
    @NotBlank
    private String problem;         // 문제내용

    private List<String> suggest;   // 보기

    private String categoryName;    // 카테고리명

    @NotBlank
    private List<String> answer;    // 정답
    @NotBlank
    private String comment;         // 해설

    private int score;              // 점수
    @Range(min = 0, max = 5)
    private int difficulty;         // 난이도

    public Problem toChoice() {
        return new ChoiceProblem(this);
    }

    public Problem toEssay() {
        return new ChoiceProblem(this);
    }
}
