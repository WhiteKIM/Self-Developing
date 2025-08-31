package whitekim.self_developing.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import whitekim.self_developing.dto.request.LoginMember;
import whitekim.self_developing.model.Member;
import whitekim.self_developing.service.MemberService;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/info")
    public ResponseEntity<Member> getDetailInfo(@RequestParam Long id) {
        Optional<Member> getMember = memberService.findById(id);

        if(getMember.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(getMember.get());
    }

    @PostMapping("/join")
    public ResponseEntity<String> joinMember(@RequestBody Member member) {
        try {
            memberService.registerMember(member);
        } catch (ConstraintViolationException e) {
            return ResponseEntity.status(401).body(null);
        }

        return ResponseEntity.ok("Join Success");
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginMember(@RequestBody LoginMember loginMember, HttpServletResponse response) {
        log.info("[Login] : {}", loginMember);

        try {
            memberService.loginMember(loginMember, response);
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(null);
        }

        return ResponseEntity.ok("Success Login");
    }

    @PostMapping("/password/reset")
    public ResponseEntity<String> resetPassword(@RequestBody Long memberId) {
        String resetPassword = memberService.resetMemberPassword(memberId);

        return ResponseEntity.ok(resetPassword);
    }
}
