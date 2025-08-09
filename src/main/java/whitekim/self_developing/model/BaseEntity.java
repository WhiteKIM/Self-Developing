package whitekim.self_developing.model;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

/**
 * @author whitekim
 * 엔티티 클래스에 대해서 기본적인 정보 작성
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

    // 삭제여부
    private boolean isDeleted = false;

    // 삭제 시간
    private LocalDateTime atDeleted;

    private String firstCreateId;
    private String lastModifierId;
    private Long version;

    /**
     * 삭제요청을 처리할 기본 기능
     */
    protected void requestDeleted() {
       this.isDeleted = true;
       this.atDeleted = LocalDateTime.now();
    }

    /**
     * 삭제된 항목을 다시 원복하기 위한 기능
     */
    protected void requestDeletedRestore() {
        this.isDeleted = false;
        this.atDeleted = null;
    }
}
