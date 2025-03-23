package whitekim.self_developing.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
}
