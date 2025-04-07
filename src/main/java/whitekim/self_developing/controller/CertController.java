package whitekim.self_developing.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import whitekim.self_developing.dto.request.CertForm;
import whitekim.self_developing.model.Certification;
import whitekim.self_developing.service.CertService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cert")
public class CertController {
    private final CertService certService;

    @GetMapping("/v1/list")
    public ResponseEntity<List<Certification>> getCertificationList() {
        List<Certification> certificationList = certService.findAllCertification();

        return ResponseEntity.ok(certificationList);
    }

    @PostMapping("/v1/write")
    public ResponseEntity<String> writeCertification(@RequestBody CertForm certForm) {
        certService.saveCertification(certForm);

        return ResponseEntity.ok("등록 완료");
    }
}
