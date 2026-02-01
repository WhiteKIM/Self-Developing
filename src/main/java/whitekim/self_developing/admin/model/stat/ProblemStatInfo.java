package whitekim.self_developing.admin.model.stat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProblemStatInfo {
    private Long totalCnt;
    private Long wrongCnt;
    private Long rightCnt;
}
