package whitekim.self_developing.dto.request;

import whitekim.self_developing.model.Member;

public record MemberRegister(
        String username,
        String password,
        String email
) {
    public static Member toEntity(MemberRegister memberRegister) {
        return Member.builder()
                .username(memberRegister.username)
                .password(memberRegister.password)
                .email(memberRegister.email)
                .build();
    }
}
