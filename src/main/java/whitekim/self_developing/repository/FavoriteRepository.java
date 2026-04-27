package whitekim.self_developing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import whitekim.self_developing.model.Member;
import whitekim.self_developing.model.Paper;
import whitekim.self_developing.model.relation.MemberFavoritePaper;

@Repository
public interface FavoriteRepository extends JpaRepository<MemberFavoritePaper, Long> {
    MemberFavoritePaper findByMemberAndPaper(Member member, Paper paper);
}
