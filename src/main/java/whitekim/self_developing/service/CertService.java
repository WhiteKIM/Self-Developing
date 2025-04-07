package whitekim.self_developing.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import whitekim.self_developing.dto.request.CertForm;
import whitekim.self_developing.model.Certification;
import whitekim.self_developing.repository.CertRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class CertService {
    private final CertRepository certRepository;

    public void saveCertification(CertForm cert) {
        certRepository.save(cert.toEntity());
    }

    public List<Certification> findAllCertification() {
        return certRepository.findAll();
    }

    public Certification findByCertificationName(String certificationName) {
        Optional<Certification> optCert = certRepository.findByCertName(certificationName);

        if(optCert.isEmpty())
            throw new RuntimeException();

        return optCert.get();
    }
}
