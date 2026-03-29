package whitekim.self_developing.model.problem;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import whitekim.self_developing.dto.request.SubmitAnswer;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Answer {
    private String content;
    private String comment;

    public static Answer toEntity(SubmitAnswer answer) {
        return new Answer(answer.content(), null);
    }
}
