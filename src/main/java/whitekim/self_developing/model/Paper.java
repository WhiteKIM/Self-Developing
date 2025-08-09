package whitekim.self_developing.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import whitekim.self_developing.dto.request.PaperForm;
import whitekim.self_developing.model.enumerate.PaperType;

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

    @OneToMany
    @JoinColumn(name = "vote_id")
    private List<Vote> voteList = new ArrayList<>();

    @OneToMany
    @JoinColumn(name = "tag_id")
    private List<Tag> tagList = new ArrayList<>();

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
