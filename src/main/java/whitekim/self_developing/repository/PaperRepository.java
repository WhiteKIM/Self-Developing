package whitekim.self_developing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import whitekim.self_developing.model.Category;
import whitekim.self_developing.model.Certification;
import whitekim.self_developing.model.Paper;
import whitekim.self_developing.model.PaperType;

import java.util.List;

@Repository
public interface PaperRepository extends JpaRepository<Paper, Long> {
    List<Paper> findByCertificationAndType(Certification certification, PaperType type);

    List<Paper> findAllByCategory(Category category);
}
