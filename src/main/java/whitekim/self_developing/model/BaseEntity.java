package whitekim.self_developing.model;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * 기본적인 메타데이터 관리
 * 생성시간
 * 수정시간
 * 생성자
 * 수정자
 * 버전
 * 삭제여부
 * 삭제시간
 */
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    private LocalDateTime atCreated;
    @LastModifiedDate
    private LocalDateTime atModified;

    private String firstCreateId;
    private String lastModifierId;
    private Long version;
    private Boolean isDeleted = false;
    private LocalDateTime atDeleted = null;

    protected final void softDelete() {
        this.isDeleted = true;
        atDeleted = LocalDateTime.now();
    }

    protected final void restoreDelete() {
        this.isDeleted = false;
        atDeleted = null;
    }
}
