package whitekim.self_developing.admin.mapper;

import org.springframework.stereotype.Repository;
import whitekim.self_developing.admin.model.stat.CertPaperPrbStatInfo;
import whitekim.self_developing.admin.model.stat.DiffProblemStatInfo;
import whitekim.self_developing.admin.model.stat.MemberSolvedStatInfo;
import whitekim.self_developing.admin.model.stat.ProblemStatInfo;

@Repository
public interface StatMapperRepository {

    /**
     * 사용자별 정오표 집계 구성
     * @param memberId
     * @return
     */
    MemberSolvedStatInfo selectBySolvedProblemStat(Long memberId);

    /**
     * 일주일 내 신규가입자 집계
     * @return
     */
    Long selectNewMemberCount();

    /**
     * 현재까지 등록된
     * 문제, 문제집, 자격증 정보 조회
     */
    CertPaperPrbStatInfo selectCountPaperAndProblem();

    /**
     * 일주일 간 해결된 문제 수
     * @return
     */
    ProblemStatInfo selectProblemHistoryAgg();

    /**
     * 난이도별 문제 정오표 통계
     * @return
     */
    DiffProblemStatInfo selectDegreeLevelAgg();
}
