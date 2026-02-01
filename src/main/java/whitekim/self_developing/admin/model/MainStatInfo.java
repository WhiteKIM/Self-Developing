package whitekim.self_developing.admin.model;

import lombok.Data;
import whitekim.self_developing.admin.model.stat.CertPaperPrbStatInfo;
import whitekim.self_developing.admin.model.stat.DiffProblemStatInfo;
import whitekim.self_developing.admin.model.stat.MemberStatInfo;
import whitekim.self_developing.admin.model.stat.ProblemStatInfo;

import java.util.List;

@Data
public class MainStatInfo {
    private CertPaperPrbStatInfo certPaperPrbStatInfo;
    private List<DiffProblemStatInfo> diffProblemStatInfo;
    private ProblemStatInfo problemStatInfo;
    private MemberStatInfo newMemberCount;
}
