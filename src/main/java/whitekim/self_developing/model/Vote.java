package whitekim.self_developing.model;

import jakarta.persistence.Entity;
import whitekim.self_developing.model.enumerate.Reaction;

/**
 * 문제 추천 기록 엔티티
 * 비회원의 경우에는 Ip만 기록하고, 회원이라면 사용자id까지 기록한다
 */
@Entity
public class Vote extends BaseEntity {
    private Reaction type;
    private String voteUserId;
    private String voteUserIp;
}
