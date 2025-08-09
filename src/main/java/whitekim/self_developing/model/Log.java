package whitekim.self_developing.model;

import jakarta.persistence.Entity;

/**
 * @author whitekim
 * 시스템로그 기본 타입
 * 관리힝목
 * 1. 발생지점
 * 2. 기능 구분(CRUD)
 * 3. 해당 이력정보
 */
@Entity
public class Log extends BaseEntity {
    private String job;         // 발생된 작업(엔티티)명
    private String method;      // 발생된 메소드 
    private String message;     // 발생된 로그 메시지
}
