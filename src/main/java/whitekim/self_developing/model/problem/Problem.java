package whitekim.self_developing.model.problem;

import lombok.*;
import whitekim.self_developing.dto.response.MarkingProblem;
import whitekim.self_developing.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author WhiteKIM
 * 문제 엔티티
 * 기본적으로 문제가 가지는 정보는 해당 클래스에 작성
 * 필요시 해당 클래스 상속 후 구현하여 세부문제 클래스 생성을 권장
 */
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Problem {
    private Long problemId;
    private String title;   // 제목
    private String round;   // 회차정보
    private String subject; //과목정보
    private String problemContent; // 문제내용 - 텍스트
    private Certification certification;    // 자격증정보
    private Image image; // 문제내용 - 이미지
    private Paper paper;
    private List<Vote> voteList = new ArrayList<>();
    private List<Tag> tagList = new ArrayList<>();

    // 키워드값
    private List<String> keywordList = new ArrayList<>();
    private int score = 0;      // 점수
    private int difficulty = 0;   // 난이도
    private Answer answer;                  // 정답
    private GradingStrategy strategy;      // 채점방식(주관식, 객관식)

    /**
     * DTO -> ENTITY
     * @param form - 입력DTo
     * @return - Entity
     */
    public static Problem toDomain(ProblemEntity form) {
        return Problem.builder()
                .problemId(form.getId())
                .title(form.getTitle())
                .round(form.getRound())
                .subject(form.getSubject())
                .problemContent(form.getProblemContent())
                .certification(form.getCertification())
                .image(form.getImage())
                .paper(form.getPaper())
                .voteList(form.getVoteList())
                .tagList(form.getTagList())
                .answer(form.getAnswer())
                .score(form.getScore())
                .difficulty(form.getDifficulty())
                .build();
    }

    /**
     * 문제 채점 기능
     * 제출 답안과 등록된 정답을 비교해서 결과를 반환
     * @param submitAnswer - 제출 답안
     * @return
     */
    public MarkingProblem mark(Answer submitAnswer) {
        return new MarkingProblem(
                this.getProblemId(),
                strategy.grade(answer, submitAnswer),
                this.score,
                strategy.getType().toString(),
                submitAnswer.getContent(),
                answer.getContent(),
                answer.getComment()
        );
    }

    public void addVote(Vote vote) {
        this.voteList.add(vote);
    }

    public void cancelVote(Vote vote) {
        this.voteList.remove(vote);
    }
}
