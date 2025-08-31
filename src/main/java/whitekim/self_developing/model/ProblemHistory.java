package whitekim.self_developing.model;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
public class ProblemHistory extends BaseEntity {
    @ManyToOne
    @JoinColumn(name = "problem_id")
    private Problem problem;

    @CreatedDate
    private LocalDateTime solvedTime;
}
