package whitekim.self_developing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import whitekim.self_developing.model.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    public void deleteByTagName(String tagName);
}
