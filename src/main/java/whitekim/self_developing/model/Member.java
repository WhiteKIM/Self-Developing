package whitekim.self_developing.model;

import jakarta.persistence.*;
import lombok.*;
import whitekim.self_developing.model.enumerate.Permission;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Member extends BaseEntity {
    @Column(unique = true)
    private String username;
    private String password;
    private String email;

    @OneToMany
    @JoinColumn(name = "paper_id")
    private List<Paper> favoriteList = new ArrayList<>();   // 즐겨찾기

    @OneToMany
    @JoinColumn(name = "problem_id")
    private List<Problem> wrongList = new ArrayList<>();      // 오답리스트

    @OneToMany
    @JoinColumn(name = "paper_id")
    private List<Paper> recentList = new ArrayList<>();     // 최근진행내역
    
    @OneToMany
    @JoinColumn(name = "problem_history_id")
    private List<ProblemHistory> problemHistoryList = new ArrayList<>();   // 최근 해결문제

    private Permission permission = Permission.USER;        // 사용자권한(기본값 : 사용자)

    @OneToOne
    @JoinColumn(name = "point_id")
    private Point point;                                    // 사용자 포인트
    
    private int wrongPasswordCount = 0;                     // 사용자 비밀번호 오입력 건수  5이상부터는 계정 비활성화 및 비밀번호 변경 요청

    /**
     * 메타정보 추가
     * IP
     * 계정유효성
     * 권한
     * 마지막 접속 시간
     */
    private String latestAccessIp;
    private Boolean isAvailableAccount;
    private LocalDateTime latestAccessTime;

    /**
     * 비밀번호 암호화 적용
     * @param encPassword - 인코딩된 패스워드
     */
    public void encryptPassword(String encPassword) {
        this.password = encPassword;
    }
    
    /**
     * 즐겨찾기에 대상을 추가
     * @param paper - 추가할 문제집 정보
     */
    public void addFavorite(Paper paper) {
        favoriteList.add(paper);
    }

    /**
     * 틀린 문제를 리스트에 추가
     * @param problemList - 틀린 문제 정보
     */
    public void addWrongList(List<? extends Problem> problemList) {
        wrongList.addAll(problemList);
    }

    /**
     * 최근에 수행한 문제집을 추가
     * @param paper - 최근에 수행한 문제집 정보
     */
    public void addRecentPaper(Paper paper) {
        recentList.add(paper);
    }

    /**
     * 비밀번호 초기화
     * @param password - 인코딩된 UUID 문자열
     */
    public void resetPassword(String password) {
        this.wrongPasswordCount = 0;
        this.password = password;
    }

    public void addWrongProblem(Problem problem) {
        wrongList.add(problem);
    }

    /**
     * 수행한 문제 이력 추가
     * @param history - 최근 해결한 문제내역 정보
     */
    public void addProblemHistory(ProblemHistory history) {
        // 내역은 최대 30개까지 등록
        if(problemHistoryList.size() >= 30) {
            problemHistoryList.removeFirst();
        }

        problemHistoryList.add(history);
    }
}
