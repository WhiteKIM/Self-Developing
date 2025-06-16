package whitekim.self_developing.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import whitekim.self_developing.model.Problem;
import whitekim.self_developing.service.ProblemService;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/problem")
public class ProblemController {
    private final Map<String, ProblemService<? extends Problem>> problemService;

    @GetMapping("/list")
    public ResponseEntity<List<? extends Problem>> getProblemByPaper(@RequestParam Long paperId, @RequestParam String type) {
        ProblemService<? extends Problem> targetService = problemService.get(type + "ProblemService");

        List<? extends Problem> problemList = targetService.findAllByPaper(paperId);

        return ResponseEntity.ok(problemList);
    }

    /**
     * 단건 문제 답안 제출
     * @param problemId - 해당 문제
     * @param answer - 문제 답안
     * @return - 채점 결과
     */
    @PostMapping("/v1/markProblem")
    public ResponseEntity<?> markingProblem(@RequestParam Long problemId, @RequestBody String answer) {


        return null;
    }
}
