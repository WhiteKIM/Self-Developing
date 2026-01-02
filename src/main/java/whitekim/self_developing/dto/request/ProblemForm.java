package whitekim.self_developing.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;
import whitekim.self_developing.model.ChoiceProblem;
import whitekim.self_developing.model.EssayProblem;
import whitekim.self_developing.model.Problem;

import java.util.List;

/**
 * @author whitekim
 * 문제 생성 시 받을 입력에 대한 DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProblemForm {
    private Long id;                // 문제 ID - 수정 시 필요
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

    private String imageFileName;   // 이미지파일명

    @NotBlank
    private List<String> answer;    // 정답
    @NotBlank
    private String comment;         // 해설

    private int score;              // 점수
    @Range(min = 0, max = 5)
    private int difficulty;         // 난이도

    private String status;          // 상태여부(U, D)
    
    private ImageMetaInfo imageMetaInfo;

    public Problem toChoice() {
        return new ChoiceProblem(this);
    }

    public Problem toEssay() {
        return new EssayProblem(this);
    }

    /**
     * 이미지 메타정보 내부클래스
     * 이미지 추가, 삭제 정보를 반영
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ImageMetaInfo {
        private boolean hasImage;
        private boolean delete;
    }
}
