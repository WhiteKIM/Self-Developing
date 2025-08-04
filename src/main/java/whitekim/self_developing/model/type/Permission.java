package whitekim.self_developing.model.type;

public enum Permission {
    USER("ROLE_USER"), ADMIN("ROLE_ADMIN");

    private final String auth;

    Permission(String auth) {
        this.auth = auth;
    }

    public String getAuth() {
        return auth;
    }
}
