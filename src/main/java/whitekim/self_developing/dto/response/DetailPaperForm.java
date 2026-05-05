package whitekim.self_developing.dto.response;

import whitekim.self_developing.model.Member;
import whitekim.self_developing.model.Paper;
import whitekim.self_developing.model.Tag;
import whitekim.self_developing.model.Vote;
import whitekim.self_developing.model.problem.ProblemEntity;

import java.util.List;

public record DetailPaperForm(
    Long paperId,
    String title,  // 문제지 제목
    Long pageId,
    List<DetailProblemForm> problemList,
    List<Vote> voteList,
    List<Tag> tagList,
    MemberDetail member
) {
    public static DetailPaperForm toDto(Paper paper, Member member) {
        List<ProblemEntity> problems = paper.getProblemList();
        List<DetailProblemForm> formList;

        formList = problems.stream().map(DetailProblemForm::toDto).toList();

        return new DetailPaperForm(
                paper.getId(),
                paper.getTitle(),
                paper.getPage().getId(),
                formList,
                paper.getVoteList(),
                paper.getTagList(),
                member.toDto()
        );
    }
}
