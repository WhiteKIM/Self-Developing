package whitekim.self_developing.dto.request;

import lombok.Data;
import whitekim.self_developing.model.ChoiceProblem;
import whitekim.self_developing.model.Problem;

import java.util.List;

@Data
public class ProblemForm {
    private String problemType;     // 문제 타입
    private String title;           // 제목
    private String round;           // 회차
    private String subject;         // 과목정보
    private String problem;         // 문제내용
    private List<String> suggest;   // 보기
    private String categoryName;    // 카테고리명
    private List<String> answer;    // 정답
    private String comment;         // 해설
    private int score;              // 점수

    public Problem toChoice() {
        return new ChoiceProblem(this);
    }

    public Problem toEssay() {
        return new ChoiceProblem(this);
    }
}
