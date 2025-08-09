package whitekim.self_developing.dto.request;

import lombok.Data;

@Data
public class SearchPaper {
    private String certificationName;
    private String category;
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
