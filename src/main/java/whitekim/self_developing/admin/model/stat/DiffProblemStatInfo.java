package whitekim.self_developing.admin.model.stat;

import lombok.Data;

@Data
public class DiffProblemStatInfo {
    private String difficulty;
    private Long wrongCnt;
    private Long rightCnt;
}
