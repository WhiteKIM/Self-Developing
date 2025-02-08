package whitekim.self_developing.dto.request;

import lombok.Data;
import whitekim.self_developing.model.PaperType;

@Data
public class SearchPaper {
    private String certificationName;
    private String type;
}
