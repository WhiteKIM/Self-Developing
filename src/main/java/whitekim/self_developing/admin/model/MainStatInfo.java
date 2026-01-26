package whitekim.self_developing.admin.model;

import lombok.Data;
import whitekim.self_developing.admin.model.stat.CertPaperPrbStatInfo;
import whitekim.self_developing.admin.model.stat.DiffProblemStatInfo;
import whitekim.self_developing.admin.model.stat.ProblemStatInfo;

@Data
public class MainStatInfo {
    private CertPaperPrbStatInfo certPaperPrbStatInfo;
    private DiffProblemStatInfo diffProblemStatInfo;
    private ProblemStatInfo problemStatInfo;
    private Long newMemberCount;
}
