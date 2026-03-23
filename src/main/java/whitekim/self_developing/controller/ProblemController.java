package whitekim.self_developing.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import whitekim.self_developing.dto.request.SubmitAnswer;
import whitekim.self_developing.dto.response.MarkingProblem;
import whitekim.self_developing.model.problem.Answer;
import whitekim.self_developing.model.problem.Problem;
import whitekim.self_developing.service.CertificationService;
import whitekim.self_developing.service.ProblemService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/problem")
public class ProblemController {
    private final ProblemService problemService;
    private final CertificationService certifcaitonService;

    @GetMapping("/list")
    public ResponseEntity<List<Problem>> getProblemByPaper(@RequestParam Long paperId) {
        List<Problem> problemList = problemService.findAllByPaper(paperId);

        return ResponseEntity.ok(problemList);
    }

    /**
     * 단건 문제 답안 제출
     * @param answer : 제출답안
     * @return - 채점 결과
     */
    @PostMapping("/v1/markProblem")
    public ResponseEntity<MarkingProblem> markingProblem(@RequestBody SubmitAnswer answer) {
        MarkingProblem markingProblem = problemService.markingProblem(answer.id(), Answer.toEntity(answer));

        return ResponseEntity.ok(markingProblem);
    }

    /**
     * 특정 자격증 내에서 랜덤한 문제를 추출해서 반환
     */
    @PostMapping("/v1/random")
    public ResponseEntity<Problem> getRandomProblem(@RequestParam String certName) {
        Problem problem = certifcaitonService.selectRandomProblemByCertification(certName);

        return ResponseEntity.ok(problem);
    }
}
