package whitekim.self_developing.admin.model.stat;

import lombok.Data;

@Data
public class ProblemStatInfo {
    private Long totalCnt;
    private Long wrongCnt;
    private Long rightCnt;
}
