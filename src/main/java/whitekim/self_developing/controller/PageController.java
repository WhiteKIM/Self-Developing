package whitekim.self_developing.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import whitekim.self_developing.dto.request.PageForm;
import whitekim.self_developing.dto.request.PaperForm;
import whitekim.self_developing.dto.response.DetailPageForm;
import whitekim.self_developing.exception.NotFoundDataException;
import whitekim.self_developing.model.Page;
import whitekim.self_developing.service.PageService;
import whitekim.self_developing.service.PaperService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/page")
public class PageController {
    private final PageService pageService;
    private final PaperService paperService;

    @GetMapping("/v1/list")
    public ResponseEntity<List<Page>> getPageList() {
        List<Page> pageList = pageService.getPageList();

        return ResponseEntity.ok(pageList);
    }

    @GetMapping("/v1/detail")
    public ResponseEntity<DetailPageForm> getPageDetail(@RequestParam(name = "id") Long id) {
        DetailPageForm page = null;

        try {
            page = pageService.getDetailPage(id);
        } catch (NotFoundDataException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(page);
    }

    @PostMapping("/v1/write")
    public ResponseEntity<String> writePage(@RequestBody PageForm pageForm) {
        pageService.registerPage(pageForm);

        return ResponseEntity.ok("페이지를 등록하였습니다.");
    }

    @PostMapping("/v1/paper/register")
    public ResponseEntity<Long> registerPaper(@RequestParam(name = "pageId") Long pageId, @RequestBody PaperForm paperForm) {
        Long paperId = paperService.registerPaper(pageId, paperForm);

        return ResponseEntity.ok(paperId);
    }
}
