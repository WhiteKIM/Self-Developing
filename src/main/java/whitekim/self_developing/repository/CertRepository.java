package whitekim.self_developing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import whitekim.self_developing.model.Certification;

import java.util.Optional;

@Repository
public interface CertRepository extends JpaRepository<Certification, Long> {
    Optional<Certification> findByCertName(String certName);
}
