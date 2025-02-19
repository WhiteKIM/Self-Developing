package whitekim.self_developing.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
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
        this.password = password;
    }
}
