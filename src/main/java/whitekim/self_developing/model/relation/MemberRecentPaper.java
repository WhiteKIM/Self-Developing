package whitekim.self_developing.model.relation;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import whitekim.self_developing.model.BaseEntity;
import whitekim.self_developing.model.Member;
import whitekim.self_developing.model.Paper;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@AllArgsConstructor
public class MemberRecentPaper extends BaseEntity {
    @ManyToOne
    @JsonIgnore
    private Member member;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "paper_id")
    private Paper paper;
}
