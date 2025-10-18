package whitekim.self_developing.model.relation;


import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import whitekim.self_developing.model.BaseEntity;
import whitekim.self_developing.model.Member;
import whitekim.self_developing.model.Paper;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MemberRecentPaper extends BaseEntity {
    @ManyToOne
    private Member member;

    @ManyToOne
    @JoinColumn(name = "paper_id")
    private Paper paper;
}
