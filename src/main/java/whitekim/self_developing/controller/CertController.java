package whitekim.self_developing.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import whitekim.self_developing.model.Certification;
import whitekim.self_developing.service.CertService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cert")
public class CertController {
    private final CertService certService;

    @GetMapping("/list")
    public ResponseEntity<List<Certification>> getCertificationList() {
        List<Certification> certificationList = certService.findAllCertification();

        return ResponseEntity.ok(certificationList);
    }
}
