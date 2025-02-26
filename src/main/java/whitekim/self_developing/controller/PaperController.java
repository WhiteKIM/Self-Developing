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

    @GetMapping("/list")
    public ResponseEntity<List<Paper>> getPaperByCertification(@RequestBody SearchPaper searchPaper) {
        List<Paper> paperList = paperService.searchPaper(searchPaper);

        return ResponseEntity.ok(paperList);
    }

    @PostMapping("/write")
    public ResponseEntity<String> writePaper(Paper paper) {
        paperService.registerPaper(paper);

        return ResponseEntity.ok("Success");
    }
}
