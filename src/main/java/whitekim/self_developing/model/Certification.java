package whitekim.self_developing.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 자격증 정보
 * 자격증 명, 시험시간, 과목개수 등의 정보를 나타냄
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Certification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;                    // PK
    private String certName;            // 자격증명
    private LocalDateTime examTime;     // 시험시간
    private Long subjectCount;          // 과목 개수
}
