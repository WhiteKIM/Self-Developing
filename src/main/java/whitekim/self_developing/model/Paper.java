package whitekim.self_developing.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import whitekim.self_developing.dto.request.PaperForm;

import java.util.ArrayList;
import java.util.List;

/**
 * 여러 문제가 등록된 문제지 or 시험지
 *
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Paper extends BaseEntity {
    private String title;   // 문제지 제목
    private PaperType type;

    @ManyToOne
    @JoinColumn(name = "page_id")
    @JsonIgnore
    private Page page;

    @OneToMany(mappedBy = "paper")
    private List<Problem> problemList = new ArrayList<>();

    public Paper(PaperForm paperForm) {
        this.title = paperForm.getTitle();
        this.type = PaperType.valueOf(paperForm.getType());
    }

    public void addProblem(Problem problem) {
        problemList.add(problem);
        problem.appendedPaper(this);
    }


    public void registerPage(Page page) {
        this.page = page;
    }
}
