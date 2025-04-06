package whitekim.self_developing.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import whitekim.self_developing.model.Certification;
import whitekim.self_developing.model.Problem;

/**
 * 문제 공통 리포지토리
 * 구체 리포지토리는 각각 문제에 맞는 기능을 제공, 해당 리포지토리는 공통 기능에 대해서 작성
 * @param <T>
 */
@NoRepositoryBean
public interface ProblemRepository<T extends Problem> extends JpaRepository<T, Long> {
    Page<T> findAllByCertification(Certification certification, Pageable pageable);
    Long countByCertification(Certification certification);
}
