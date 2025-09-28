package whitekim.self_developing.dto.response;

import whitekim.self_developing.model.enumerate.Permission;

public record MemberDetail(
        String username,
        String password,
        String email,
        Permission permission,        // 사용자권한(기본값 : 사용자)         // 사용자 포인트
        int wrongPasswordCount
) {
}
