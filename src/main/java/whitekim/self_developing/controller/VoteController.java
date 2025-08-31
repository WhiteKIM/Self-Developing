package whitekim.self_developing.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import whitekim.self_developing.service.PaperService;
import whitekim.self_developing.service.factory.problem.ProblemServiceFactory;

@RestController
@RequiredArgsConstructor
@RequestMapping("/vote")
public class VoteController {
    private final PaperService paperService;
    private final ProblemServiceFactory problemServiceFactory;

    @PostMapping("/add")
    public ResponseEntity<String> addVote() {
        return null;
    }

    @PostMapping("/remove")
    public ResponseEntity<String> removeVote() {
        return null;
    }
}
