package whitekim.self_developing.model.problem;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import whitekim.self_developing.dto.request.SubmitAnswer;
import whitekim.self_developing.model.BaseEntity;

@Entity
@Getter
@AllArgsConstructor
public class Answer extends BaseEntity {
    private String content;
    private String comment;

    public static Answer toEntity(SubmitAnswer answer) {
        return new Answer(answer.content(), null);
    }
}
