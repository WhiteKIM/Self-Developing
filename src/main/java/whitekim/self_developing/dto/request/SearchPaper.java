package whitekim.self_developing.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import whitekim.self_developing.model.enumerate.PaperType;
import whitekim.self_developing.validator.EnumValidator;

@Data
public class SearchPaper {
    @NotBlank
    private String certificationName;
    @NotBlank
    private String category;
    @EnumValidator(targetEnum = PaperType.class, message = "Only use Allow Type")
    private String type;

    public SearchPaper(String certificationName, String category, String type) {
        this.certificationName = certificationName;
        this.category = category;
        this.type = type;
    }

    public boolean isValidation() {
        return !certificationName.isEmpty() || !category.isEmpty() || !type.isEmpty();
    }
}
