package whitekim.self_developing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import whitekim.self_developing.model.Vote;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {
}
