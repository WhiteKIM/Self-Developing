package whitekim.self_developing.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import whitekim.self_developing.model.Certification;
import whitekim.self_developing.model.ChoiceProblem;
import whitekim.self_developing.model.EssayProblem;

@Repository
public interface EssayProblemRepository extends ProblemRepository<EssayProblem> {
    @Query(value = "select p.* from essay_problem p where certification_id = :certification.id order by random() limit 1", nativeQuery = true)
    EssayProblem selectRandomProblemByCertification(Certification certification);
}
