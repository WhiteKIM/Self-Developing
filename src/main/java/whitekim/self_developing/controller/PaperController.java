package whitekim.self_developing.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import whitekim.self_developing.dto.request.ProblemForm;
import whitekim.self_developing.dto.response.MarkingPaper;
import whitekim.self_developing.model.Paper;
import whitekim.self_developing.service.MemberService;
import whitekim.self_developing.service.PaperService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/paper")
public class PaperController {
    private final PaperService paperService;
    private final MemberService memberService;

    @GetMapping("/v1/list")
    public ResponseEntity<List<Paper>> getPaperList(@RequestParam(required = true, name = "pageId") Long pageId) {
        List<Paper> paperList = paperService.getPaperList(pageId);

        return ResponseEntity.ok(paperList);
    }

    @GetMapping("/v1/detail")
    public ResponseEntity<Paper> getDetailPaper(@RequestParam("id") Long id) {
        Paper paper = paperService.getPaperDetail(id);

        return ResponseEntity.ok(paper);
    }

    @PostMapping("/v1/registerProblem")
    public ResponseEntity<String> registerProblemList(
            @RequestParam("paperId") Long paperId,
            @RequestPart("problemList") String problemListJson,
            @RequestPart(value = "uploadFiles", required = false) List<MultipartFile> uploadFiles
    ) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        List<ProblemForm> problemList = om.readValue(problemListJson, new TypeReference<List<ProblemForm>>() {});

        paperService.addProblem(paperId, problemList, uploadFiles);

        return ResponseEntity.ok("문제를 등록하였습니다.");
    }

    @PostMapping("/v1/updateProblem")
    public ResponseEntity<String> updateProblemList(
            @RequestParam("paperId") Long paperId,
            @RequestPart("problemList") String problemListJson,
            @RequestPart(value = "uploadFiles", required = false) List<MultipartFile> uploadFiles
    ) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        List<ProblemForm> problemList = om.readValue(problemListJson, new TypeReference<List<ProblemForm>>() {});

        log.info("[PaperController] UploadFile : {}", uploadFiles);

        paperService.updateProblem(paperId, problemList, uploadFiles);

        return ResponseEntity.ok("문제를 등록하였습니다.");
    }

    /**
     * 문제지 답안 제출
     * @param paperId - 해당 문제지 정보
     * @param answerList - 문제지 답안 리스트
     * @return 채점 결과
     */
    @PostMapping("/v1/markPaper")
    public ResponseEntity<?> markingPaper(@RequestParam("paperId") Long paperId, @RequestBody List<String> answerList) {
        MarkingPaper markingPaper = paperService.markingPaper(paperId, answerList);

        return ResponseEntity.ok(markingPaper);
    }

    @PostMapping("/v1/favorite/add")
    public ResponseEntity<String> addFavoritePaper(@RequestParam("paperId") Long paperId) {
        log.info("[PaperController] PaperController.addFavoritePaper : {}", paperId);

        memberService.addPaperIntoFavoriteList(paperId);

        return ResponseEntity.ok("즐겨찾기 추가 성공");
    }

    @PostMapping("/v1/favorite/remove")
    public ResponseEntity<String> removeFavoritePaper(@RequestParam("paperId") Long paperId) {
        log.info("[PaperController] PaperController.addFavoritePaper : {}", paperId);

        memberService.removePaperFromFavoriteList(paperId);

        return ResponseEntity.ok("즐겨찾기 제거 성공");
    }
}
