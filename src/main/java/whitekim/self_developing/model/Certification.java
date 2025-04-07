package whitekim.self_developing.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
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
@Builder
@Getter
public class Certification extends BaseEntity {     // PK
    @Column(unique = true)
    private String certName;            // 자격증명
    private Long examTime;     // 시험시간
    private Long subjectCount;          // 과목 개수
}
