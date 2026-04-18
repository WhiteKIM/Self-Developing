package whitekim.self_developing.dto.response;

import whitekim.self_developing.model.Certification;
import whitekim.self_developing.model.Image;
import whitekim.self_developing.model.Tag;
import whitekim.self_developing.model.Vote;
import whitekim.self_developing.model.problem.Answer;
import whitekim.self_developing.model.problem.ProblemEntity;

import java.util.List;

public record DetailProblemForm(
        Long id,
        String title,   // 제목
        String round,   // 회차정보
        String subject, //과목정보
        String problemContent, // 문제내용 - 텍스트
        Certification certification,    // 자격증정보
        Image image, // 문제내용 - 이미지
        Long paperId,
        List<Vote> voteList,
        List<Tag> tagList,
        List<String> keywordList,
        int score,      // 점수
        int difficulty,   // 난이도
        Answer answer,                  // 정답
        String type     // 채점방식(주관식, 객관식)
) {
    public static DetailProblemForm toDto(ProblemEntity entity) {
        return new DetailProblemForm(
                entity.getId(),
                entity.getTitle(),
                entity.getRound(),
                entity.getSubject(),
                entity.getProblemContent(),
                entity.getCertification(),
                entity.getImage(),
                entity.getPaper().getId(),
                entity.getVoteList(),
                entity.getTagList(),
                entity.getKeywordList(),
                entity.getScore(),
                entity.getDifficulty(),
                entity.getAnswer(),
                entity.getStrategyType()
        );
    }
}
