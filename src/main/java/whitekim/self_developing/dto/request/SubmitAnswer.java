package whitekim.self_developing.dto.request;

import jakarta.validation.constraints.NotNull;

public record SubmitAnswer(
        @NotNull
        Long id,
        String content
) {
}
