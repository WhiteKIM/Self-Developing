package whitekim.self_developing.dto.request;

import lombok.Data;
import whitekim.self_developing.model.Certification;

import java.time.LocalDateTime;

@Data
public class CertForm {
    private String certName;
    private Long examTime;
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
