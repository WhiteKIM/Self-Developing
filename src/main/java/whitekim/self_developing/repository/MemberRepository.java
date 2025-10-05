package whitekim.self_developing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import whitekim.self_developing.model.Member;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByUsername(String username);

    @Query("select m from Member m left join fetch m.recentList where m.id = :id")
    Optional<Member> findMemberWithRecentList(Long id);

    @Query("select m from Member m left join fetch m.favoriteList where m.id = :id")
    Optional<Member> findMemberWithFavoriteList(Long id);
}
