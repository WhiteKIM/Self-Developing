package whitekim.self_developing.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import whitekim.self_developing.model.Certification;
import whitekim.self_developing.model.ChoiceProblem;

@Repository
public interface ChoiceProblemRepository extends ProblemRepository<ChoiceProblem> {
    @Query(value = "select p.* from choice_problem p where certification_id = :certification.id order by random() limit 1", nativeQuery = true)
    ChoiceProblem selectRandomProblemByCertification(Certification certification);
}
