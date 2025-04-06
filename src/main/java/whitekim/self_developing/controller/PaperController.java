package whitekim.self_developing.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import whitekim.self_developing.dto.request.ProblemForm;
import whitekim.self_developing.dto.request.SearchPaper;
import whitekim.self_developing.model.Paper;
import whitekim.self_developing.service.PaperService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/paper")
public class PaperController {
    private final PaperService paperService;

    @GetMapping("/v1/list")
    public ResponseEntity<List<Paper>> getPaperList(@RequestParam(required = true) Long pageId) {
        List<Paper> paperList = paperService.getPaperList(pageId);

        return ResponseEntity.ok(paperList);
    }

    @GetMapping("/v1/detail")
    public ResponseEntity<Paper> getDetailPaper(@RequestParam Long id) {
        Paper paper = paperService.getPaperDetail(id);

        return ResponseEntity.ok(paper);
    }

    @PostMapping("/v1/paper/registerProblem")
    public ResponseEntity<String> registerProblemList(@RequestParam Long paperId, @RequestPart List<ProblemForm> problemList) {
        paperService.addProblem(paperId, problemList);

        return ResponseEntity.ok("문제를 등록하였습니다.");
    }
}
