package whitekim.self_developing.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
public class ProblemHistory extends BaseEntity {
    @OneToMany
    @JoinColumn(name = "problem_id")
    private Problem problem;

    @CreatedDate
    private LocalDateTime solvedTime;
}
