package whitekim.self_developing.model;

import jakarta.persistence.Entity;
import whitekim.self_developing.model.enumerate.Reaction;

/**
 * 문제, 문제집 등에 대한 추천 비추천 관리를 위한 클래스
 */
@Entity
public class Vote extends BaseEntity {
    private Reaction reaction;
}
