package whitekim.self_developing.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import whitekim.self_developing.model.enumerate.PageType;
import whitekim.self_developing.validator.EnumValidator;

@Data
public class PageForm {
    @NotBlank
    @EnumValidator(targetEnum = PageType.class, message = "Only Use Allow Type")
    private String pageType;
    @NotBlank
    private String certification;
    @NotBlank
    private String category;
}
