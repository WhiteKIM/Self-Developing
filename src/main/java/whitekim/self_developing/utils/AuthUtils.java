package whitekim.self_developing.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import whitekim.self_developing.auth.PrincipalMember;


public class AuthUtils {
    private AuthUtils authUtils;

    private AuthUtils() {}


    public static UserDetails getAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (PrincipalMember) authentication.getPrincipal();
    }
}
