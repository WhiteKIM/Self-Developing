package whitekim.self_developing.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import whitekim.self_developing.auth.PrincipalMember;
import whitekim.self_developing.dto.request.MemberRegister;
import whitekim.self_developing.dto.request.UpdateMember;
import whitekim.self_developing.dto.response.MemberInfo;
import whitekim.self_developing.model.Log;
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
    public ResponseEntity<MemberInfo> getDetailInfo() {
        PrincipalMember principalMember =
                (PrincipalMember) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if(principalMember == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(memberService.getMemberDetail(principalMember.getMember()));
    }

    @PostMapping("/join")
    public ResponseEntity<String> joinMember(@RequestBody MemberRegister joinMember) {
        try {
            memberService.registerMember(joinMember);
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

        List<Paper> favoriteList = memberService.getMemberFavoriteList(principalMember.getId());

        return ResponseEntity.ok(favoriteList);
    }

    /**
     * 로그인한 사용자의 최근 이력 조회
     * @return
     */
    @GetMapping("/v1/recent/list")
    public ResponseEntity<List<Paper>> getMemberRecentList() {
        PrincipalMember principalMember =
                (PrincipalMember) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<Paper> favoriteList = memberService.getMemberRecentList(principalMember.getId());

        return ResponseEntity.ok(favoriteList);
    }

    @GetMapping("/v1/point/history")
    public ResponseEntity<List<Log>> getMemberPointHistoryList() {
        PrincipalMember principalMember =
                (PrincipalMember) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<Log> memberPointHistory = memberService.getMemberPointHistory(principalMember.getId());

        return ResponseEntity.ok(memberPointHistory);
    }
}
