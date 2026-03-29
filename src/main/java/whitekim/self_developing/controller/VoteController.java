package whitekim.self_developing.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import whitekim.self_developing.service.PaperService;
import whitekim.self_developing.service.ProblemService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/vote")
public class VoteController {
    private final PaperService paperService;
    private final ProblemService problemService;

    @PostMapping("/add")
    public ResponseEntity<String> addVote(@RequestParam Long id, @RequestParam String type, @RequestParam String like) {
        if(type.equals("problem")) {
            problemService.addVote(id, like);
        } else if(type.equals("paper")) {
            paperService.addVote(id, like);
        }

        return null;
    }

    @PostMapping("/remove")
    public ResponseEntity<String> removeVote() {
        return null;
    }
}
