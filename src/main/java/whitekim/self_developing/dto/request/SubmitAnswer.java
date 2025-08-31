package whitekim.self_developing.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import whitekim.self_developing.model.enumerate.ProblemType;
import whitekim.self_developing.validator.EnumValidator;

public record SubmitAnswer(
        @NotNull
        Long id,
        @NotBlank
        String answer,
        @EnumValidator(targetEnum = ProblemType.class, message = "only use allow Type")
        String type
) {
}
