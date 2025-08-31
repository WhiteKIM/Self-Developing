package whitekim.self_developing.dto.request;

import jakarta.validation.constraints.NotBlank;

public record LoginMember(
        @NotBlank
        String username,
        @NotBlank
        String password
) {
}
