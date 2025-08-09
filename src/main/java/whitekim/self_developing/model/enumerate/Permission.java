package whitekim.self_developing.model.enumerate;

/**
 * @author whitekim
 * @comments 권한에 대한 열거를 작성하는 부분
 */
public enum Permission {
    USER("ROLE_USER"), ADMIN("ROLE_ADMIN");

    private final String role;

    Permission(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
