package whitekim.self_developing.repository;

import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface StatMapperRepository {

    // 문제 정오표 통계
    Map selectBySolvedProblemStat(Long memberId);
}
