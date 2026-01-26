package whitekim.self_developing.admin.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import whitekim.self_developing.admin.model.MainStatInfo;
import whitekim.self_developing.admin.service.AdminService;
import whitekim.self_developing.dto.request.LoginMember;

import java.util.Map;

/**
 * @author WhiteKIM
 * 관리자 기능으로 요청되는 화면, 데이터을 제공
 * 기능 정리
 * 1. 정오 집계 : 문제별 정답율, 오답율을 집계하고 처리
 * 2. 사용자 추세 : 가입한 사용자, 활성된 사용자 수, 미접속 사용자 등에 정보 제공
 * 3. 데이터 처리 : 부적절한 사용자 및 문제를 제거하거나 비활성화 처리를 위해 사용
 * 4. 로그 관리 : 시스템 관리를 위해서 로그를 출력할 화면 제공
 * 5. 부적절한 포인트 관리 : 포인트 이력 및 생성 등을 관리하고 부적절하게 생성된 포인트를 관리하거나 지급하는 기능
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/main")
    public String adminMainPage(Model model) {
        MainStatInfo statInfo = adminService.selectStatInfo();

        model.addAttribute("content", "main :: body");
        model.addAttribute("statInfo", statInfo);

        return "commons/layout";
    }

    @GetMapping("/login")
    public String adminLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public ResponseEntity<Map> adminLogin(@RequestBody LoginMember loginMember, HttpServletRequest request, Model model) {
        adminService.loginAdmin(loginMember, request);

        model.addAttribute("content", "main::body");

        return ResponseEntity.ok(
                Map.of("redirectUrl", "/admin/main")
        );
    }

    @GetMapping("/stat")
    public String adminStatPage(Model model) {

        return "/stat/main";
    }

    @GetMapping("/management")
    public String adminManagementPage(Model model) {
        return "/manage/main";
    }
}
