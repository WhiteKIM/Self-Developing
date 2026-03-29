package whitekim.self_developing.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import whitekim.self_developing.model.Certification;
import whitekim.self_developing.model.problem.ProblemEntity;

/**
 * 문제 리포지토리
 */
@Repository
public interface ProblemRepository extends JpaRepository<ProblemEntity, Long> {
    Page<ProblemEntity> findAllByCertification(Certification certification, Pageable pageable);
    Long countByCertification(Certification certification);
}
