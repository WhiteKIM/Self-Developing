package whitekim.self_developing.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
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
    public ResponseEntity<List<Paper>> getPaperByCertification(@RequestParam(required = true) Long pageId) {
        List<Paper> paperList = paperService.searchPaper(pageId);

        return ResponseEntity.ok(paperList);
    }

    @GetMapping("/v1/detail")
    public ResponseEntity<Paper> getDetailPaper(@RequestParam Long id) {
        Paper paper = paperService.getPaperDetail(id);

        return ResponseEntity.ok(paper);
    }

    @PostMapping("/v1/register")
    public ResponseEntity<String> registerPaper(Paper paper) {
        paperService.registerPaper(paper);

        return ResponseEntity.ok("Success");
    }
}
