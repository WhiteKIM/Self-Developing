package whitekim.self_developing.dto.response;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 해당 문제지 답안에 대한 채점 결과를 저장
 * 문제지 제목, 회차, 제출 시간 등등
 */
public record MarkingPaper(
        String title,
        String type,
        int score,
        int correctCount,
        int wrongCount,
        LocalDateTime markTime,
        List<MarkingProblem> markingProblemList
) {
}
