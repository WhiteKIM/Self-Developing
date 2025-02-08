package whitekim.self_developing.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import whitekim.self_developing.model.Certification;
import whitekim.self_developing.model.Problem;

@Repository
public interface ProblemRepository<T extends Problem> extends JpaRepository<T, Long> {
    Page<T> findAllByCertification(Certification certification, Pageable pageable);
    Long countByCertification(Certification certification);
}
