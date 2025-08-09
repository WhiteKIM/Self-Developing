package whitekim.self_developing.auth;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import whitekim.self_developing.model.Member;

import java.util.Collection;
import java.util.List;

public class PrincipalMember implements UserDetails {
    private final Member member;

    public PrincipalMember(Member member) {
        this.member = member;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return member.getPassword();
    }

    @Override
    public String getUsername() {
        return member.getUsername();
    }

    public Long getId() {
        return member.getId();
    }

    /**
     * 비밀번호 5회 이상 오입력시 비활성화 처리
     * @return : 5회 이상 false
     */
    @Override
    public boolean isAccountNonLocked() {
        return member.getWrongPasswordCount() <= 5;
    }
}
