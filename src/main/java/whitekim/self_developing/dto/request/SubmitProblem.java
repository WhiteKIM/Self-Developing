package whitekim.self_developing.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import whitekim.self_developing.model.enumerate.ProblemType;
import whitekim.self_developing.validator.EnumValidator;

@Data
public class SubmitProblem {
    @NotNull
    private Long id;
    @EnumValidator(targetEnum = ProblemType.class, message = "Only Use Allow Type")
    private String type;
}
