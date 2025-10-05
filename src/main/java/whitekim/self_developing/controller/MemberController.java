package whitekim.self_developing.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import whitekim.self_developing.auth.PrincipalMember;
import whitekim.self_developing.dto.request.UpdateMember;
import whitekim.self_developing.dto.response.MemberDetail;
import whitekim.self_developing.model.Member;
import whitekim.self_developing.model.Paper;
import whitekim.self_developing.service.MemberService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    /**
     * 현재 로그인한 사용자 정보 조회
     * @return
     */
    @GetMapping("/info")
    public ResponseEntity<MemberDetail> getDetailInfo() {
        PrincipalMember principalMember =
                (PrincipalMember) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(principalMember == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(principalMember.getMember().toDto());
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

    @PutMapping("/update")
    public ResponseEntity<String> joinMember(@RequestBody UpdateMember member) {
        PrincipalMember principalMember =
                (PrincipalMember) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Member updateMember = principalMember.getMember();

        try {
            memberService.updateMember(updateMember, member);
        } catch (ConstraintViolationException e) {
            return ResponseEntity.status(401).body(null);
        }

        return ResponseEntity.ok("Join Success");
    }

//    @PostMapping("/login")
//    public ResponseEntity<?> loginMember(@RequestBody LoginMember loginMember, HttpServletResponse response) {
//        log.info("[Login] : {}", loginMember);
//
//        try {
//            memberService.loginMember(loginMember, response);
//        } catch (RuntimeException e) {
//            return ResponseEntity.status(401).body(null);
//        }
//
//        return ResponseEntity.ok("Success Login");
//    }

    @PostMapping("/password/reset")
    public ResponseEntity<String> resetPassword(@RequestBody Long memberId) {
        String resetPassword = memberService.resetMemberPassword(memberId);

        return ResponseEntity.ok(resetPassword);
    }

    /**
     * 로그인한 사용자의 즐겨찾기 목록 조회
     * @return
     */
    @GetMapping("/v1/favorite/list")
    public ResponseEntity<List<Paper>> getMemberFavoriteList() {
        PrincipalMember principalMember =
                (PrincipalMember) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<Paper> favoriteList = memberService.getMemberFavoriteList(principalMember.getId()).favoriteList();

        return ResponseEntity.ok(favoriteList);
    }

    /**
     * 로그인한 사용자의 최근 이력 조회
     * @return
     */
    @GetMapping("/v1/recent")
    public ResponseEntity<List<Paper>> getMemberRecentList() {
        PrincipalMember principalMember =
                (PrincipalMember) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<Paper> favoriteList = memberService.getMemberRecentList(principalMember.getId()).recentList();

        return ResponseEntity.ok(favoriteList);
    }
}
