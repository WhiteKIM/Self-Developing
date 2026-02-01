package whitekim.self_developing.admin.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import whitekim.self_developing.admin.model.MemberInfo;

import java.util.List;

@Mapper
@Repository
public interface MemberManagementMapper {
    List<MemberInfo> selectMemberList();
    void deleteMember(Long userId);
}
