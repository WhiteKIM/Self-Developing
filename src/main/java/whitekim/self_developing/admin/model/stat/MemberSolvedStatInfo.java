package whitekim.self_developing.admin.model.stat;

import lombok.Data;

@Data
public class MemberSolvedStatInfo {
    private Long memberId;      // 사용자 ID
    private Long correctCount;  // 정답수
    private Long wrongCount;    // 오답수
    private Long AllCount;      // 전체 문제풀이 수
}
