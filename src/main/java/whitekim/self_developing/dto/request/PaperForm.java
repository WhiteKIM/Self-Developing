package whitekim.self_developing.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import whitekim.self_developing.model.enumerate.PaperType;
import whitekim.self_developing.validator.EnumValidator;

@Data
public class PaperForm {
    @NotBlank
    private String title;
    @EnumValidator(targetEnum = PaperType.class, message = "Only Use Allow Type")
    private String type;
}
