package whitekim.self_developing.dto.response;

import whitekim.self_developing.model.Member;
import whitekim.self_developing.model.Paper;
import whitekim.self_developing.model.enumerate.Permission;

import java.math.BigDecimal;
import java.util.List;

public record MemberInfo(
        String username,
        String password,
        String email,
        Permission permission,        // 사용자권한(기본값 : 사용자)         // 사용자 포인트
        int wrongPasswordCount,
        List<Paper> favoriteList,
        List<Paper> recentList,
        BigDecimal point
) {
    public static MemberInfo from(Member member) {
        return new MemberInfo(
                member.getUsername(),
                member.getPassword(),
                member.getEmail(),
                member.getPermission(),
                member.getWrongPasswordCount(),
                // Lazy 로딩 문제 방지를 위해 복사(List.of(...) or stream)
                member.getFavoriteList() != null ? List.copyOf(member.getFavoriteList()) : List.of(),
                member.getRecentList() != null ? List.copyOf(member.getRecentList()) : List.of(),
                member.getPoint().getPointQuantity()
        );
    }
}
