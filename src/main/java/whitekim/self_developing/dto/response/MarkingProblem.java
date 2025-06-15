package whitekim.self_developing.dto.response;

/**
 * 문제 채점에 대한 결과 제공
 * 문제 아이디, 타입, 제출, 정답, 코멘트 
 */
public record MarkingProblem(
        Long problemId,
        boolean isCorrect,
        String type,
        String submit,
        String answer,
        String comment  // 정답일 때는 굳이 출력할 필요가 없음
) {
}
