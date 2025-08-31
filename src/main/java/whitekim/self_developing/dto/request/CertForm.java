package whitekim.self_developing.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import whitekim.self_developing.model.Certification;

@Data
public class CertForm {
    @NotBlank
    private String certName;

    @Min(value = 0)
    private Long examTime;
    @Min(value = 0)
    private Long subjectCount;

    public Certification toEntity() {
        return Certification
                .builder()
                .certName(certName)
                .examTime(examTime)
                .subjectCount(subjectCount)
                .build();
    }
}
