package whitekim.self_developing.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import whitekim.self_developing.service.TagService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TagController {
    private final TagService tagService;

    @GetMapping("/v1/tag/list")
    public ResponseEntity<List<String>> getTagList() {
        List<String> tagList = tagService.getTagList();
        
        return ResponseEntity.ok(tagList);
    }

    @GetMapping("/v1/tag/write")
    public ResponseEntity<String> writeTag(@RequestParam(name = "tagName") String tagName) {
        tagService.writeTag(tagName);
        
        return ResponseEntity.ok("등록 성공");
    }
}
